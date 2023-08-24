package com.vama.vamabackend.services;

import com.vama.vamabackend.persistence.entity.products.ProductStockDetails;
import com.vama.vamabackend.persistence.repository.ProductStockDetailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductStockDetailsService {

    private ProductStockDetailsRepository repository;


    public List<ProductStockDetails> findAllByProductId(Long productId){
        List<ProductStockDetails> list = repository.findAllByProductId(productId);
        return list;
    }
}
