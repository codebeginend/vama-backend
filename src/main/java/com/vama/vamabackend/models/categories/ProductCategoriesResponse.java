package com.vama.vamabackend.models.categories;

import lombok.Data;

@Data
public class ProductCategoriesResponse {
    private Integer rootId;
    private String rootName;
    private Integer childId;
    private String childName;
    private Integer twoChildId;
    private String twoChildName;
}
