package com.vama.vamabackend.models;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class NomenclatureMessage {
    private Long id;
    private String code;
    private String productName;
    private BigDecimal price;
    private BigDecimal stock;
    private Long progressId;
    private boolean isUpdateProduct;
    private boolean isUpdatePrice;
    private boolean isUpdateStock;
    private boolean isNew;
}
