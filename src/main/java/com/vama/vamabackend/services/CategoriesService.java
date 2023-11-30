package com.vama.vamabackend.services;

import com.vama.vamabackend.models.categories.CategoriesAdminResponse;
import com.vama.vamabackend.models.products.ProductsAdminResponse;
import com.vama.vamabackend.persistence.entity.categories.CategoriesEntity;
import com.vama.vamabackend.persistence.repository.CategoriesJdbcRepository;
import com.vama.vamabackend.persistence.repository.CategoriesRepository;
import com.vama.vamabackend.persistence.repository.types.CategoriesAdminType;
import com.vama.vamabackend.persistence.repository.types.ProductsAdminType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoriesService {

    private CategoriesRepository categoriesRepository;
    private CategoriesJdbcRepository jdbcRepository;


    public List<CategoriesEntity> findAll() {
        List<CategoriesEntity> categoriesEntities = categoriesRepository.findAllByParentIdIsNull();
        return categoriesEntities;
    }

    public List<CategoriesAdminResponse> findAllForAdmin(String searchText, Boolean isPublish){
        List<CategoriesAdminType> typeList = jdbcRepository.getAllForAdmin(searchText, isPublish);

        List<CategoriesAdminResponse> response = typeList.stream().map(m -> CategoriesAdminResponse.builder()
                .id(m.getId())
                .isPublished(m.getIsPublished())
                .name(m.getName())
                .childs(objectToList(m.getChilds()))
                .childsTwo(objectToList(m.getChildsTwo()))
                .totalProducts(m.getTotalProducts())
                .build()).collect(Collectors.toList());
        return response;
    }

    public CategoriesAdminResponse findDetailsForAdmin(Long categoryId){
        CategoriesAdminType type = jdbcRepository.getDetailsForAdmin(categoryId);

        CategoriesAdminResponse response =  CategoriesAdminResponse.builder()
                .id(type.getId())
                .isPublished(type.getIsPublished())
                .name(type.getName())
                .childs(objectToList(type.getChilds()))
                .childsTwo(objectToList(type.getChildsTwo()))
                .totalProducts(type.getTotalProducts())
                .build();
        return response;
    }


    private List<String> objectToList(Object object){
        if (object != null){
            String text = object.toString();
            text = text.replace("{", "").replace("}", "").replace("\"", "");
            String[] array = text.split(",");
            return Arrays.asList(array);
        }
        return null;
    }
}
