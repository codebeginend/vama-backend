package com.vama.vamabackend.services;

import com.vama.vamabackend.models.order.*;
import com.vama.vamabackend.persistence.entity.orders.*;
import com.vama.vamabackend.persistence.entity.products.ProductsEntity;
import com.vama.vamabackend.persistence.entity.users.UserEntity;
import com.vama.vamabackend.persistence.repository.*;
import com.vama.vamabackend.persistence.repository.types.OrdersAdminType;
import com.vama.vamabackend.persistence.repository.types.OrdersType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
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
        if (request.getDeliveryType().equals(DeliveryTypeEnum.FAST)){
            orders.setDeliveryDate(request.getDeliveryDate());
            orders.setDeliveryTime(request.getDeliveryTime());
        }
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

    // Текущий заказ на жкране профиль
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

    public void  adminChangeStatus(Long orderId, OrderStatusesEnum status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity userEntity = userDetailsService.findByUsername(username);

        boolean isAllowUpdate = false;
        Optional<OrdersEntity> ordersEntity = ordersRepository.findById(orderId);
        if (ordersEntity.isEmpty()){
//            return null;
        }
        OrderStatusesEntity orderStatus = orderStatusesRepository.findLastByOrderId(ordersEntity.get().getId());

        switch (status){
            case CREATED:
                if (orderStatus == null){
                    isAllowUpdate = true;
                }
                break;
            case ACCEPT:
                if (orderStatus.getName().equals(OrderStatusesEnum.CREATED)){
                    isAllowUpdate = true;
                }
                break;
            case DELIVERED:
                if (orderStatus.getName().equals(OrderStatusesEnum.ACCEPT)){
                    isAllowUpdate = true;
                }
                break;
            case COMPLETED:
                if (orderStatus.getName().equals(OrderStatusesEnum.DELIVERED)){
                    isAllowUpdate = true;
                }
                break;
            case CANCELLED:
                if (orderStatus.getName().equals(OrderStatusesEnum.CREATED) || orderStatus.getName().equals(OrderStatusesEnum.ACCEPT)){
                    isAllowUpdate = true;
                }
                break;
        }

        if (isAllowUpdate){
            OrderStatusesEntity orderStatusesEntity = new OrderStatusesEntity();
            orderStatusesEntity.setName(status);
            orderStatusesEntity.setOrderId(orderId);
            orderStatusesEntity.setUserId(userEntity.getId());
            orderStatusesRepository.save(orderStatusesEntity);
        }
    }

    public ChangeStatusResponse changeToCancelledStatus(Long orderId, OrderStatusesEnum status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity userEntity = userDetailsService.findByUsername(username);
        boolean isCancelled = false;
        Optional<OrdersEntity> ordersEntity = ordersRepository.findById(orderId);

        if (!ordersEntity.isEmpty() && ordersEntity.get().getUserId() == userEntity.getId()){
            OrderStatusesEntity orderStatus = orderStatusesRepository.findLastByOrderId(ordersEntity.get().getId());
            switch (status){
                case CANCELLED:
                    if (orderStatus.getName().equals(OrderStatusesEnum.CREATED) || orderStatus.getName().equals(OrderStatusesEnum.ACCEPT)){
                        isCancelled = true;
                    }
                    break;
            }
            if (isCancelled){
                OrderStatusesEntity orderStatusesEntity = new OrderStatusesEntity();
                orderStatusesEntity.setName(status);
                orderStatusesEntity.setOrderId(orderId);
                orderStatusesEntity.setUserId(userEntity.getId());
                OrderStatusesEntity statusesEntity = orderStatusesRepository.save(orderStatusesEntity);
                return ChangeStatusResponse.builder().orderId(orderId)
                        .name(status)
                        .createdAt(statusesEntity.getCreatedAt()).build();
            }
        }
        return null;
    }

    public ChangeStatusResponse changeStatus(Long orderId, OrderStatusesEnum status) {
        Optional<OrdersEntity> ordersEntity = ordersRepository.findById(orderId);
        OrderStatusesEntity orderStatus = orderStatusesRepository.findLastByOrderId(ordersEntity.get().getId());
        boolean isUpdate = false;
        switch (status){
            case ACCEPT:
                if (orderStatus.getName().equals(OrderStatusesEnum.CREATED)){
                    isUpdate = true;
                }
                break;
            case DELIVERED:
                if (orderStatus.getName().equals(OrderStatusesEnum.ACCEPT)){
                    isUpdate = true;
                }
                break;
            case COMPLETED:
                if (orderStatus.getName().equals(OrderStatusesEnum.DELIVERED) || orderStatus.getName().equals(OrderStatusesEnum.ACCEPT)){
                    isUpdate = true;
                }
                break;
            case CANCELLED:
                if (orderStatus.getName().equals(OrderStatusesEnum.CREATED) || orderStatus.getName().equals(OrderStatusesEnum.ACCEPT) || orderStatus.getName().equals(OrderStatusesEnum.DELIVERED)){
                    isUpdate = true;
                }
                break;
        }
        if (isUpdate){
            OrderStatusesEntity orderStatusesEntity = new OrderStatusesEntity();
            orderStatusesEntity.setName(status);
            orderStatusesEntity.setOrderId(orderId);
            OrderStatusesEntity statusesEntity = orderStatusesRepository.save(orderStatusesEntity);
            return ChangeStatusResponse.builder().orderId(orderId)
                    .name(status)
                    .createdAt(statusesEntity.getCreatedAt()).build();
        }
        return null;
    }
}
