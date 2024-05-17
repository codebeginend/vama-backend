package com.vama.vamabackend.models.categories;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CategoryItemAdminResponse {
    private Long id;
    private String name;
    private BigDecimal totalProducts;
    private List<String> productsNames;
}
