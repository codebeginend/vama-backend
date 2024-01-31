package com.vama.vamabackend.controllers;

import com.vama.vamabackend.models.categories.CategoriesAdminResponse;
import com.vama.vamabackend.models.categories.CategoriesForProduct;
import com.vama.vamabackend.persistence.entity.categories.CategoriesEntity;
import com.vama.vamabackend.services.CategoriesService;
import com.vama.vamabackend.services.ProductStockDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("categories")
@AllArgsConstructor
public class CategoriesController {
    private CategoriesService categoriesService;
    private ProductStockDetailsService stockDetailsService;

    @GetMapping(value = "all")
    public List<CategoriesEntity> findAll() {
        return categoriesService.findAll();
    }

    @GetMapping(value = "admin/all")
    private List<CategoriesAdminResponse> findAllForAdmin(@RequestParam(required = false, defaultValue = "") String searchText, @RequestParam(defaultValue = "") String isPublish){
        Boolean isPublishValue = null;
        if (Arrays.asList("false", "true").contains(isPublish)){
            isPublishValue = Boolean.valueOf(isPublish);
        }
        return categoriesService.findAllForAdmin(searchText, isPublishValue);
    }

    @GetMapping(value = "admin/parent/all")
    private List<CategoriesEntity> findAllForAdminByParentId(@RequestParam() String parentId){
        Integer id = null;
        if (!parentId.equals("null")){
            id = Integer.parseInt(parentId);
        }
        List<CategoriesEntity> list = categoriesService.findAllByParentId(id);
        return list;
    }

    @GetMapping(value = "admin/products/all")
    private List<CategoriesForProduct> findAllForProductAdmin(@RequestParam() List<String> ids){
        List<Long> longId = new ArrayList<>();
        ids.forEach(val -> {
            longId.add(Long.parseLong(val));
        });
        List<CategoriesForProduct> list = categoriesService.findAllForProduct(longId);
        return list;
    }

    @GetMapping(value = "admin/details")
    private CategoriesAdminResponse findDetailsForAdmin(@RequestParam Long id){
        return categoriesService.findDetailsForAdmin(id);
    }
}
