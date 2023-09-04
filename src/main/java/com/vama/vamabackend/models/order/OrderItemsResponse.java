package com.vama.vamabackend.models.order;

import com.vama.vamabackend.persistence.entity.products.ProductsEntity;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItemsResponse {
    private ProductsEntity product;
    private BigDecimal count;
    private BigDecimal price;
    private BigDecimal discount;
    private Long productId;
    private Long orderId;
}
