package com.vama.vamabackend.services;

import com.vama.vamabackend.persistence.entity.categories.CategoriesEntity;
import com.vama.vamabackend.persistence.repository.CategoriesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoriesService {

    private CategoriesRepository categoriesRepository;


    public List<CategoriesEntity> findAll() {
        List<CategoriesEntity> categoriesEntities = categoriesRepository.findAllByParentIdIsNull();
        return categoriesEntities;
    }

}
