package com.vama.vamabackend.persistence.repository.types;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductsAdminType {
    private Long id;
    private Boolean isPublished;
    private String code;
    private String name;
    private String unitType;
    private BigDecimal unitValue;
    private BigDecimal stock;
    private BigDecimal price;
    private BigDecimal optPrice;
    private String categoryName;
}