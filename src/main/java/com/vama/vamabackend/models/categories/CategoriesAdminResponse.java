package com.vama.vamabackend.models.categories;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CategoriesAdminResponse {
    private Long id;
    private Boolean isPublished;
    private String name;
    private List<String> childs;
    private List<String> childsTwo;
    private BigDecimal totalProducts;
}
