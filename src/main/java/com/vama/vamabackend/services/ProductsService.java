package com.vama.vamabackend.services;

import com.vama.vamabackend.models.products.ProductDetailsAdminResponse;
import com.vama.vamabackend.models.products.ProductsAdminResponse;
import com.vama.vamabackend.persistence.entity.categories.CategoriesEntity;
import com.vama.vamabackend.persistence.entity.products.ProductsEntity;
import com.vama.vamabackend.persistence.repository.CategoriesRepository;
import com.vama.vamabackend.persistence.repository.ProductsJdbcRepository;
import com.vama.vamabackend.persistence.repository.ProductsRepository;
import com.vama.vamabackend.persistence.repository.types.ProductsAdminType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductsService {
    private ProductsRepository productsRepository;
    private ProductsJdbcRepository productsJdbcRepository;
    private CategoriesRepository categoriesRepository;


    public List<ProductsEntity> findAllByCategoryId(Long categoryId){
        return productsRepository.findAllByCategoryId(categoryId);
    }

    public List<ProductsEntity> findAllByName(String name){
        if (name != ""){
            name = name.toUpperCase(Locale.ROOT) + '%';
            return productsRepository.findAllByName(name);
        }else {
            return new ArrayList<>();
        }
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

    public List<ProductsAdminResponse> findAllForAdmin(String searchText, Boolean isPublish){
        List<ProductsAdminType> typeList = productsJdbcRepository.getAllForAdmin(searchText, isPublish);
        List<ProductsAdminResponse> response = typeList.stream().map(m -> ProductsAdminResponse.builder()
                .id(m.getId())
                .code(m.getCode())
                .isPublished(m.getIsPublished())
                .name(m.getName())
                .categoryName(m.getCategoryName())
                .price(m.getPrice())
                .stock(m.getStock())
                .unitType(m.getUnitType())
                .unitValue(m.getUnitValue())
                .build()).collect(Collectors.toList());
        return response;
    }

    public ProductDetailsAdminResponse findDetailsForAdmin(Long id){
        ProductsEntity entity = productsRepository.findById(id).orElse(null);
        CategoriesEntity categoriesEntity = categoriesRepository.findById(entity.getCategoryId()).orElse(null);
        ProductDetailsAdminResponse response = ProductDetailsAdminResponse.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .isPublished(entity.isPublished())
                .name(entity.getName())
                 .description(entity.getDescription())
                .price(entity.getPrice())
                .discount(entity.getDiscount())
                .stock(entity.getStock().intValue())
                .unit(entity.getUnit().name())
                .unitType(entity.getUnitType().name())
                .unitValue(entity.getUnitValue())
                .build();
         if (categoriesEntity != null){
             response.setCategoryName(categoriesEntity.getName());
             response.setCategoryId(categoriesEntity.getId());
         }
        return response;
    }
}
