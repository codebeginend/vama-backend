package com.vama.vamabackend.persistence.entity.products;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "products")
public class ProductsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;
    @Column(name = "description")
    private String description;
    @Column(name = "logo")
    private String logo;
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "unit")
    @Enumerated(EnumType.STRING)
    private UnitEnum unit;

    @Column(name = "unit_type")
    @Enumerated(EnumType.STRING)
    private UnitTypesEnum unitType;

    @Column(name = "unit_value")
    private BigDecimal unitValue;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "opt_price")
    private BigDecimal optPrice;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "stock")
    private BigDecimal stock;

    @Column(name = "is_published")
    private boolean isPublished;
}
