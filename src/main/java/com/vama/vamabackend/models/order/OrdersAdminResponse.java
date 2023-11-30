package com.vama.vamabackend.models.order;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vama.vamabackend.models.common.LocalDateTimeDeserializer;
import com.vama.vamabackend.models.common.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
public class OrdersAdminResponse {
    private Long id;
    private String statusName;
    private Long userId;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;
    private BigDecimal productCount;
    private BigDecimal totalSum;
    private BigDecimal totalDiscount;
    private BigDecimal deliveryCost;
    private String paymentType;
    private String paymentStatus;
    private String deliveryType;
    private String address;
    private String clientNumber;
    private String clientName;
}
