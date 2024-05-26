package com.vama.vamabackend.models.products;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductDetailsRequest {
    private Long productId;
    private String name;
    private Boolean isUpdateName;
    private String description;
    private Boolean isUpdateDescription;
    private BigDecimal price;
    private Boolean isUpdatePrice;
    private String unit;
    private Boolean isUpdateUnit;
    private String unitType;
    private Boolean isUpdateUnitType;
    private BigDecimal stock;
    private BigDecimal unitValue;
    private Boolean isUpdateUnitValue = false;
    private Boolean isUpdateStock = false;
    private Long categoryId;
    private Boolean isUpdateCategory;
}
