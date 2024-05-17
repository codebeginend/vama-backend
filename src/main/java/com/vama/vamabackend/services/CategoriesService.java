package com.vama.vamabackend.services;

import com.vama.vamabackend.models.categories.*;
import com.vama.vamabackend.models.products.ProductsAdminResponse;
import com.vama.vamabackend.persistence.entity.categories.CategoriesEntity;
import com.vama.vamabackend.persistence.repository.CategoriesJdbcRepository;
import com.vama.vamabackend.persistence.repository.CategoriesRepository;
import com.vama.vamabackend.persistence.repository.ProductsJdbcRepository;
import com.vama.vamabackend.persistence.repository.types.CategoriesAdminType;
import com.vama.vamabackend.persistence.repository.types.ProductsAdminType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoriesService {

    private CategoriesRepository categoriesRepository;
    private CategoriesJdbcRepository jdbcRepository;
    private ProductsJdbcRepository productsJdbcRepository;


    public List<CategoriesEntity> findAll() {
        List<CategoriesEntity> categoriesEntities = categoriesRepository.findAllByParentIdIsNull();
        return categoriesEntities;
    }

    public List<CategoriesForProduct> findAllForProduct(List<Long> id) {
        List<CategoriesForProduct> categoriesEntities = jdbcRepository.getAllForProduct(id);
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

    public List<CategoryItemAdminResponse> findAllByParentId(Integer parentId){
        List<CategoriesEntity> list = new ArrayList<>();
        if (parentId == null){
            list = categoriesRepository.findAllByParentIdIsNull();
        }else {
            list = categoriesRepository.findAllByParentId(parentId);
        }

        return list.stream().map(m -> dtoCategoryItem(m)).collect(Collectors.toList());
    }

    public CategoryItemAdminResponse dtoCategoryItem(CategoriesEntity entity) {
        List<String> products = productsJdbcRepository.getAllFromCategory(entity.getId());
        CategoryItemAdminResponse response = CategoryItemAdminResponse.builder()
                .id(entity.getId())
                .name(entity.getName()).build();
        response.setTotalProducts(BigDecimal.valueOf(products.size()));
        response.setProductsNames(products);
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
        List<String> products = productsJdbcRepository.getAllFromCategory(categoryId);
        response.setTotalProducts(BigDecimal.valueOf(products.size()));
        response.setProductsNames(products);
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

    public CategoriesAdminResponse create(CreateCategoryRequest request) {
        CategoriesEntity entity = new CategoriesEntity();
        entity.setName(request.getName());
        categoriesRepository.save(entity);
        return findDetailsForAdmin(entity.getId());
    }

    public CategoriesEntity createSubCategory(CreateCategoryRequest request) {
        CategoriesEntity entity = new CategoriesEntity();
        entity.setName(request.getName());
        entity.setParentId(request.getParentId());
        categoriesRepository.save(entity);
        return entity;
    }

    public void delete(Long categoryId) {
        categoriesRepository.deleteById(categoryId);
    }

    public CategoriesAdminResponse updateName(UpdateCategoryRequest request, Long categoryId) {
        CategoriesEntity entity = categoriesRepository.findById(categoryId).orElse(null);
        if (entity == null){
            return null;
        }
        entity.setName(request.getName());
        categoriesRepository.save(entity);
        return findDetailsForAdmin(categoryId);
    }

    public CategoriesAdminResponse changePublish(Long categoryId) {
        CategoriesEntity entity = categoriesRepository.findById(categoryId).orElse(null);
        if (entity != null){
            entity.setPublished(!entity.isPublished());
            categoriesRepository.save(entity);
            return findDetailsForAdmin(categoryId);
        }
        return null;
    }
}
