package com.vama.vamabackend.persistence.repository.types;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrdersAdminType {
    private Long id;
    private String name;
    private Long userId;
    private LocalDateTime createdAt;
    private BigDecimal productCount;
    private BigDecimal totalSum;
    private String paymentType;
    private String paymentStatus;
    private String deliveryType;
}
