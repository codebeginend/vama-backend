package com.vama.vamabackend.controllers;

import com.vama.vamabackend.persistence.entity.products.ProductStockDetails;
import com.vama.vamabackend.persistence.entity.products.ProductsEntity;
import com.vama.vamabackend.services.ProductStockDetailsService;
import com.vama.vamabackend.services.ProductsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
@AllArgsConstructor
public class ProductsController {
    private ProductsService productsService;
    private ProductStockDetailsService stockDetailsService;

    @GetMapping(value = "category")
    private List<ProductsEntity> findAllByCategoryId(@RequestParam(name = "categoryId") Long categoryId){
        return productsService.findAllByCategoryId(categoryId);
    }

    @GetMapping(value = "all")
    private List<ProductsEntity> findAll(){
        return productsService.findAll();
    }

    @GetMapping()
    private ProductsEntity findById(@RequestParam(name = "id") Long id){
        return productsService.findById(id);
    }

    @GetMapping(value = "related/all")
    private List<ProductsEntity> findAllRelated(@RequestParam(name = "productId") Long productId){
        return productsService.relatedFindAllByIdProductId(productId);
    }

    @GetMapping(value = "stock/all")
    private List<ProductStockDetails> findAllStock(@RequestParam(name = "productId") Long productId){
        return stockDetailsService.findAllByProductId(productId);
    }
}
