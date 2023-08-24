package com.vama.vamabackend.persistence.repository;

import com.vama.vamabackend.persistence.entity.products.ProductStockDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductStockDetailsRepository extends JpaRepository<ProductStockDetails, Long> {

    List<ProductStockDetails> findAllByProductId(Long productId);
}
