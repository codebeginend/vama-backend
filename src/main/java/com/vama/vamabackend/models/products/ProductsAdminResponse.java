package com.vama.vamabackend.models.products;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductsAdminResponse {
    private Long id;
    private Boolean isPublished;
    private String code;
    private String name;
    private String unitType;
    private BigDecimal unitValue;
    private int stock;
    private BigDecimal price;
    private BigDecimal optPrice;
    private String categoryName;
}
