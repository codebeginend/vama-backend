package com.vama.vamabackend.controllers;

import com.vama.vamabackend.persistence.entity.categories.CategoriesEntity;
import com.vama.vamabackend.services.CategoriesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("categories")
@AllArgsConstructor
public class CategoriesController {
    private CategoriesService categoriesService;

    @GetMapping(value = "all")
    public List<CategoriesEntity> findAll(){
        return categoriesService.findAll();
    }
}
