package com.vama.vamabackend.services;

import com.vama.vamabackend.models.order.CreateOrderRequest;
import com.vama.vamabackend.models.order.OrderItemsResponse;
import com.vama.vamabackend.models.order.OrdersResponse;
import com.vama.vamabackend.persistence.entity.orders.OrderItemsEntity;
import com.vama.vamabackend.persistence.entity.orders.OrderStatusesEntity;
import com.vama.vamabackend.persistence.entity.orders.OrderStatusesEnum;
import com.vama.vamabackend.persistence.entity.orders.OrdersEntity;
import com.vama.vamabackend.persistence.entity.products.ProductsEntity;
import com.vama.vamabackend.persistence.entity.users.UserEntity;
import com.vama.vamabackend.persistence.repository.*;
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

    public List<OrderItemsResponse> findAllOrderItemsByOrderId(Long orderId){
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
