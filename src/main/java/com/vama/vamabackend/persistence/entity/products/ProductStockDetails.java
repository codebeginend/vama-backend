package com.vama.vamabackend.persistence.entity.products;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "product_stock_details")
public class ProductStockDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "stock")
    private BigDecimal stock;

    @Column(name = "product_id")
    private Long productId;
}
