package com.vama.vamabackend.persistence.repository;

import com.vama.vamabackend.persistence.entity.products.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<ProductsEntity, Long> {

    List<ProductsEntity> findAllByCategoryId(Long categoryId);
}
