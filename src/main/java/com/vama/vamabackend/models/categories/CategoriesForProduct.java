package com.vama.vamabackend.models.categories;

import lombok.Data;

@Data
public class CategoriesForProduct {
    private Long idOne;
    private String nameOne;
    private Integer parentIdOne;
    private Long idTwo;
    private String nameTwo;
    private Integer parentIdTwo;
    private Long idThree;
    private String nameThree;
    private Integer parentIdThree;
}
