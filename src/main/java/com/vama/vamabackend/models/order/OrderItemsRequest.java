package com.vama.vamabackend.models.order;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemsRequest {
    private Long id;
    private BigDecimal count;
}
