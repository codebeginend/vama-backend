package com.vama.vamabackend.persistence.entity.categories;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "categories")
public class CategoriesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "parent_id")
    private Integer parentId;

    @OneToMany
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private List<CategoriesEntity> child;
}
