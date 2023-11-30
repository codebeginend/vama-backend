package com.vama.vamabackend.services;

import com.vama.vamabackend.models.order.CreateOrderRequest;
import com.vama.vamabackend.models.order.OrderItemsResponse;
import com.vama.vamabackend.models.order.OrdersAdminResponse;
import com.vama.vamabackend.models.order.OrdersResponse;
import com.vama.vamabackend.persistence.entity.orders.OrderItemsEntity;
import com.vama.vamabackend.persistence.entity.orders.OrderStatusesEntity;
import com.vama.vamabackend.persistence.entity.orders.OrderStatusesEnum;
import com.vama.vamabackend.persistence.entity.orders.OrdersEntity;
import com.vama.vamabackend.persistence.entity.products.ProductsEntity;
import com.vama.vamabackend.persistence.entity.users.UserEntity;
import com.vama.vamabackend.persistence.repository.*;
import com.vama.vamabackend.persistence.repository.types.OrdersAdminType;
import com.vama.vamabackend.persistence.repository.types.OrdersType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrdersService {

    private UserDetailsServiceImpl userDetailsService;
    private OrdersRepository ordersRepository;
    private OrderItemsRepository orderItemsRepository;
    private OrderStatusesRepository orderStatusesRepository;
    private OrdersJdbcRepository ordersJdbcRepository;
    private ProductsRepository productsRepository;
    private UsersRepository usersRepository;

    public void createOrder(CreateOrderRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity userEntity = userDetailsService.findByUsername(username);
        OrdersEntity orders = new OrdersEntity();
        orders.setUserId(userEntity.getId());
        orders.setAddress(request.getAddress());
        orders.setEntrance(request.getEntrance());
        orders.setDeliveryType(request.getDeliveryType());
        orders.setPaymentType(request.getPaymentType());
        ordersRepository.save(orders);
        OrderStatusesEntity orderStatusesEntity = new OrderStatusesEntity();
        orderStatusesEntity.setName(OrderStatusesEnum.CREATED);
        orderStatusesEntity.setOrderId(orders.getId());
        orderStatusesEntity.setUserId(userEntity.getId());
        orderStatusesRepository.save(orderStatusesEntity);
        List<ProductsEntity> list = productsRepository.findAllById(request.getOrderItems().stream().map(m -> m.getId()).collect(Collectors.toList()));
        List<ProductCount> productCountList = list.stream().map(val -> {
            BigDecimal count = request.getOrderItems().stream().filter(f -> f.getId().equals(val.getId())).findFirst().get().getCount();
            ProductCount productCount = new ProductCount();
            productCount.setCount(count);
            productCount.setProduct(val);
            return productCount;
        }).collect(Collectors.toList());
        List<OrderItemsEntity> itemsEntities = productCountList.stream().map(m -> new OrderItemsEntity(m.getCount(), m.getProduct().getId(), orders.getId(), m.getProduct().getPrice(), m.getProduct().getDiscount())).collect(Collectors.toList());
        orderItemsRepository.saveAll(itemsEntities);
    }

    @Data
    class ProductCount{
        private ProductsEntity product;
        private BigDecimal count;
    }

    public List<OrdersResponse> findAllMe(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity userEntity = userDetailsService.findByUsername(username);
        List<OrdersType> typeList = ordersJdbcRepository.getAllByUserId(userEntity.getId());
        List<OrdersResponse> response = typeList.stream().map(m -> OrdersResponse.builder()
                .id(m.getId()).statusName(m.getName()).createdAt(m.getCreatedAt()).build()).collect(Collectors.toList());
        return  response;
    }

    public List<OrdersAdminResponse> findAllForAdmin(String status, String searchText){
        List<OrdersAdminType> typeList = ordersJdbcRepository.getAllByForAdmin(status, searchText);
        List<OrdersAdminResponse> response = typeList.stream().map(m -> OrdersAdminResponse.builder()
                .id(m.getId())
                .statusName(m.getName())
                .createdAt(m.getCreatedAt())
                .productCount(m.getProductCount())
                .totalSum(m.getTotalSum())
                .paymentType(m.getPaymentType())
                .paymentStatus(m.getPaymentStatus())
                .deliveryType(m.getDeliveryType())
                .build()).collect(Collectors.toList());
        return  response;
    }

    public List<OrdersAdminResponse> findAllByUserIdForAdmin(Long userId){
        List<OrdersAdminType> typeList = ordersJdbcRepository.getAllByUserIdForAdmin(userId);
        List<OrdersAdminResponse> response = typeList.stream().map(m -> OrdersAdminResponse.builder()
                .id(m.getId())
                .statusName(m.getName())
                .userId(m.getUserId())
                .createdAt(m.getCreatedAt())
                .productCount(m.getProductCount())
                .totalSum(m.getTotalSum())
                .paymentType(m.getPaymentType())
                .paymentStatus(m.getPaymentStatus())
                .deliveryType(m.getDeliveryType())
                .build()).collect(Collectors.toList());
        return  response;
    }

    public OrdersAdminResponse findByIdAdmin(Long orderId) {
        Optional<OrdersEntity> ordersEntity = ordersRepository.findById(orderId);
        if (ordersEntity.isEmpty()){
            return null;
        }
        OrderStatusesEntity orderStatus = orderStatusesRepository.findLastByOrderId(orderId);
        if (orderStatus == null){
            return null;
        }
        OrdersEntity order = ordersEntity.get();

        List<OrderItemsResponse> list = findAllOrderItemsByOrderId(order.getId());
        BigDecimal productCount = list.stream().map(m -> m.getCount()).reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
        BigDecimal totalSum = list.stream().map(m -> m.getCount().multiply(m.getPrice())).reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
        BigDecimal totalDiscount = list.stream().map(m -> m.getCount().multiply(m.getDiscount())).reduce(BigDecimal.ZERO, (a, b) -> a.add(b));

        UserEntity userEntity = usersRepository.findById(order.getUserId()).orElse(null);

        OrdersAdminResponse response = OrdersAdminResponse.builder()
                .id(order.getId())
                .statusName(orderStatus.getName().name())
                .createdAt(orderStatus.getCreatedAt())
                .paymentType(order.getPaymentType().name())
                .paymentStatus(order.getPaymentStatus())
                .deliveryType(order.getDeliveryType().name())
                .address(order.getAddress() + ", " + order.getEntrance())
                .productCount(productCount)
                .totalSum(totalSum)
                .totalDiscount(totalDiscount)
                .clientName(userEntity.getName())
                .clientNumber(userEntity.getUsername())
                .build();
        return response;
    }

    public OrdersResponse findByIdMe(Long orderId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity userEntity = userDetailsService.findByUsername(username);

        Optional<OrdersEntity> ordersEntity = ordersRepository.findById(orderId);
        if (ordersEntity.isEmpty()){
            return null;
        }
        if (!ordersEntity.get().getUserId().equals(userEntity.getId())){
            return null;
        }
        OrderStatusesEntity orderStatus = orderStatusesRepository.findLastByOrderId(orderId);
        if (orderStatus == null){
            return null;
        }
        OrdersEntity order = ordersEntity.get();
        OrdersResponse response = OrdersResponse.builder()
                .id(order.getId())
                .statusName(orderStatus.getName().name())
                .createdAt(orderStatus.getCreatedAt()).build();
        return response;
    }

    public OrdersResponse findLastMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity userEntity = userDetailsService.findByUsername(username);

        OrdersEntity ordersEntity = ordersRepository.findLastByUserId(userEntity.getId());
        if (ordersEntity == null){
            return null;
        }
        OrderStatusesEntity orderStatus = orderStatusesRepository.findLastByOrderId(ordersEntity.getId());
        if (orderStatus == null){
            return null;
        }
        OrdersResponse response = OrdersResponse.builder()
                .id(ordersEntity.getId())
                .statusName(orderStatus.getName().name())
                .createdAt(orderStatus.getCreatedAt()).build();
        return response;
    }

    public List<OrderItemsResponse> findAllOrderItemsByOrderIdMe(Long orderId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity userEntity = userDetailsService.findByUsername(username);
        Optional<OrdersEntity> ordersEntity = ordersRepository.findById(orderId);
        if (ordersEntity.isEmpty()){
            return null;
        }
        if (!ordersEntity.get().getUserId().equals(userEntity.getId())){
            return null;
        }
        List<OrderItemsResponse> response = findAllOrderItemsByOrderId(orderId);
        return response;
    }

    public List<OrderItemsResponse> findAllOrderItemsByOrderId(Long orderId){
        List<OrderItemsEntity> orderItemsEntityList = orderItemsRepository.findAllByOrderId(orderId);
        List<ProductsEntity> productsEntityList = productsRepository.findAllById(orderItemsEntityList.stream().map(m -> m.getProductId()).collect(Collectors.toList()));
        List<OrderItemsResponse> response = orderItemsEntityList.stream().map(m -> {
            ProductsEntity product = productsEntityList.stream().filter(f -> f.getId().equals(m.getProductId())).findFirst().get();
            return OrderItemsResponse.builder()
                    .orderId(orderId)
                    .productId(m.getProductId())
                    .product(product)
                    .price(m.getPrice())
                    .discount(m.getDiscount())
                    .count(m.getCount()).build();
        }).collect(Collectors.toList());
        return response;
    }


}
