package com.vama.vamabackend.models.order;

import com.vama.vamabackend.persistence.entity.orders.DeliveryTypeEnum;
import com.vama.vamabackend.persistence.entity.orders.PaymentTypeEnum;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {
    private List<OrderItemsRequest> orderItems;
    private String address;
    private String entrance;
    private DeliveryTypeEnum deliveryType;
    private PaymentTypeEnum paymentType;
}
