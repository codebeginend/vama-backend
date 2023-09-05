package com.vama.vamabackend.services;

import com.vama.vamabackend.persistence.entity.products.ProductsEntity;
import com.vama.vamabackend.persistence.repository.ProductsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductsService {
    private ProductsRepository productsRepository;


    public List<ProductsEntity> findAllByCategoryId(Long categoryId){
        return productsRepository.findAllByCategoryId(categoryId);
    }

    public List<ProductsEntity> findAll(){
        return productsRepository.findAll();
    }

    public ProductsEntity findById(Long id){
        return productsRepository.findById(id).orElse(null);
    }

    public List<ProductsEntity> relatedFindAllByIdProductId(Long productId){
        return productsRepository.findAllById(Arrays.asList(productId)).stream().collect(Collectors.toList());
    }
}
