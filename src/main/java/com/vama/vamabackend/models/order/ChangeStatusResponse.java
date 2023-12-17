package com.vama.vamabackend.models.order;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vama.vamabackend.models.common.LocalDateTimeDeserializer;
import com.vama.vamabackend.models.common.LocalDateTimeSerializer;
import com.vama.vamabackend.persistence.entity.orders.OrderStatusesEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ChangeStatusResponse {
    private Long orderId;
    private OrderStatusesEnum name;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;
}
