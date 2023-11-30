package com.vama.vamabackend.persistence.repository.types;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CategoriesAdminType {
    private Long id;
    private Boolean isPublished;
    private String name;
    private Object childs;
    private Object childsTwo;
    private BigDecimal totalProducts;
}