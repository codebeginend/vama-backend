package com.vama.vamabackend.persistence.entity.products;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "products")
public class ProductsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "logo")
    private String logo;
    @Column(name = "category_id")
    private Long categoryId;
}
