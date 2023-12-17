package com.vama.vamabackend.models.order;

import com.vama.vamabackend.persistence.entity.orders.OrderStatusesEnum;
import lombok.Data;

@Data
public class ChangeStatusRequest {
    private Long orderId;
    private OrderStatusesEnum nextStatus;
}
