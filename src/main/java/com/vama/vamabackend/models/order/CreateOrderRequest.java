package com.vama.vamabackend.models.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vama.vamabackend.persistence.entity.orders.DeliveryTypeEnum;
import com.vama.vamabackend.persistence.entity.orders.PaymentTypeEnum;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class CreateOrderRequest {
    private List<OrderItemsRequest> orderItems;
    private String address;
    private String entrance;
    private DeliveryTypeEnum deliveryType;
    private PaymentTypeEnum paymentType;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate deliveryDate;
    private LocalTime[] deliveryTime;
}
