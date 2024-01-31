package com.vama.vamabackend.persistence.repository;

import com.vama.vamabackend.persistence.entity.products.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<ProductsEntity, Long> {

    List<ProductsEntity> findAllByCategoryId(Long categoryId);
    @Query("select name from ProductsEntity where UPPER(name) LIKE :searchText")
    List<String> findAllLike(String searchText);

    @Query("select product from ProductsEntity product where UPPER(product.name) LIKE :name")
    List<ProductsEntity> findAllByName(String name);

    @Query("select product from ProductsEntity product where UPPER(product.code) LIKE :code")
    ProductsEntity findAllByCode(String code);
}
