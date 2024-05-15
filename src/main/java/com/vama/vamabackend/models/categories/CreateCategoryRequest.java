package com.vama.vamabackend.models.categories;

import lombok.Data;

@Data
public class CreateCategoryRequest {
    private String name;
    private Integer parentId;
}
