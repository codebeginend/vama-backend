package com.vama.vamabackend.models.products;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductDetailsAdminResponse {
    private Long id;
    private Boolean isPublished;
    private String code;
    private String name;
    private String description;
    private String unit;
    private String unitType;
    private BigDecimal unitValue;
    private int stock;
    private BigDecimal price;
    private BigDecimal optPrice;
    private BigDecimal discount;
    private String categoryName;
    private Long categoryId;
    private Boolean isPopular;
    private String logo;
    private Long[] unionProducts;
}
