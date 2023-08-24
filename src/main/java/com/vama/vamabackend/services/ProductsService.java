package com.vama.vamabackend.services;

import com.vama.vamabackend.persistence.entity.products.ProductsEntity;
import com.vama.vamabackend.persistence.repository.ProductsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductsService {
    private ProductsRepository productsRepository;


    public List<ProductsEntity> findAllByCategoryId(Long categoryId){
        return productsRepository.findAllByCategoryId(categoryId);
    }

    public ProductsEntity findById(Long id){
        return productsRepository.findById(id).orElse(null);
    }
}
