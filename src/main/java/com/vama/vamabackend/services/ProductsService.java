package com.vama.vamabackend.services;

import com.vama.vamabackend.models.NomenclatureMessage;
import com.vama.vamabackend.models.products.*;
import com.vama.vamabackend.persistence.entity.categories.CategoriesEntity;
import com.vama.vamabackend.persistence.entity.products.ProductsEntity;
import com.vama.vamabackend.persistence.entity.products.UnitEnum;
import com.vama.vamabackend.persistence.entity.products.UnitTypesEnum;
import com.vama.vamabackend.persistence.repository.CategoriesRepository;
import com.vama.vamabackend.persistence.repository.ProductsJdbcRepository;
import com.vama.vamabackend.persistence.repository.ProductsRepository;
import com.vama.vamabackend.persistence.repository.types.ProductsAdminType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
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
                .optPrice(m.getOptPrice())
                .stock(m.getStock())
                .unitType(m.getUnitType())
                .unitValue(m.getUnitValue())
                .build()).collect(Collectors.toList());
        return response;
    }

    public ProductDetailsAdminResponse findDetailsForAdmin(Long id){
        ProductsEntity entity = productsRepository.findById(id).orElse(null);
        CategoriesEntity categoriesEntity = null;
        if (entity.getCategoryId() != null){
            categoriesEntity = categoriesRepository.findById(entity.getCategoryId()).orElse(null);
        }
        ProductDetailsAdminResponse response = dtoDetails(entity);
        if (entity.getUnit() != null){
            response.setUnit(entity.getUnit().name());
        }
        if (entity.getUnitType() != null){
            response.setUnitType(entity.getUnitType().name());
        }

         if (categoriesEntity != null){
             response.setCategoryName(categoriesEntity.getName());
             response.setCategoryId(categoriesEntity.getId());
         }
        return response;
    }

    public void updateProducts(List<NomenclatureMessage> nomenclatures){
        nomenclatures.stream().forEach((val) -> {
            ProductsEntity entity = productsRepository.findAllByCode(val.getCode());
            if (entity != null){
                if (val.isUpdateProduct()){
                    entity.setName(val.getProductName());
                }
                if(val.isUpdatePrice()){
                    entity.setPrice(val.getPrice());
                }
                if (val.isUpdateStock()){
                    entity.setStock(val.getStock());
                }
            }else {
                entity = new ProductsEntity();
                entity.setCode(val.getCode());
                entity.setName(val.getProductName());
                entity.setOptPrice(val.getPrice());
                entity.setPrice(val.getPrice());
                entity.setStock(val.getStock() != null?val.getStock() : BigDecimal.ZERO);
            }
            productsRepository.save(entity);
        });
    }

    public PublishProductResponse publishProduct(PublishProductRequest request){
        Optional<ProductsEntity> entity = productsRepository.findById(request.getProductId());
        if (!entity.isEmpty()){
            ProductsEntity update = entity.get();
            update.setPublished(request.isPublish());
            productsRepository.save(update);
            return PublishProductResponse.builder()
                    .productId(update.getId())
                    .isPublish(update.isPublished()).build();
        }
        return null;
    }

    public ProductDetailsAdminResponse update(UpdateProductDetailsRequest request){
        Optional<ProductsEntity> entity = productsRepository.findById(request.getProductId());
        if (!entity.isEmpty()){
            ProductsEntity update = entity.get();
            if (request.getIsUpdateName()){
                update.setName(request.getName());
            }
            if (request.getIsUpdateDescription()){
                update.setDescription(request.getDescription());
            }
            if (request.getIsUpdatePrice()){
                update.setPrice(request.getPrice());
            }
            if (request.getIsUpdateStock()){
                update.setStock(request.getStock());
            }
            if (request.getIsUpdateUnit()){
                update.setUnit(UnitEnum.valueOf(request.getUnit()));
            }
            if (request.getIsUpdateUnitType()){
                update.setUnitType(UnitTypesEnum.valueOf(request.getUnitType()));
            }
            if (request.getIsUpdateCategory()){
                update.setCategoryId(request.getCategoryId());
            }
            productsRepository.save(update);
            return findDetailsForAdmin(request.getProductId());
        }
        return null;
    }

    public ProductDetailsAdminResponse changePopular(Long productId) {
        Optional<ProductsEntity> entity = productsRepository.findById(productId);
        if (entity.isEmpty()){
            throw new NullPointerException();
        }
        ProductsEntity updateEntity = entity.get();
        updateEntity.setPopular(!updateEntity.isPopular());
        return dtoDetails(productsRepository.save(updateEntity));
    }

    public ProductDetailsAdminResponse dtoDetails(ProductsEntity entity){
        ProductDetailsAdminResponse response = ProductDetailsAdminResponse.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .isPublished(entity.isPublished())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .optPrice(entity.getOptPrice())
                .discount(entity.getDiscount())
                .stock(entity.getStock().intValue())
                .unitValue(entity.getUnitValue())
                .isPopular(entity.isPopular())
                .logo(entity.getLogo())
                .unionProducts(entity.getUnionProducts())
                .build();
        return response;
    }

    public List<ProductsEntity> findAllPopular() {
        List<ProductsEntity> list = productsRepository.findAllByPopularIsTrue();
        return list;
    }

    public List<ProductsEntity> productsByCategoryAndNull(Long categoryId, String searchText) {
        return productsRepository.findAllByCategoryIdOrCategoryIdIsNullAndNameLike(categoryId, searchText.toUpperCase());
    }

    public ProductsEntity changeCategory(Long productId, Long categoryId) {
        ProductsEntity entity = productsRepository.findById(productId).orElse(null);
        if (entity == null){
            return null;
        }
        if (entity.getCategoryId() == categoryId){
            entity.setCategoryId(null);
        }else {
            entity.setCategoryId(categoryId);
        }
        productsRepository.save(entity);
        return entity;
    }

    public List<ProductsEntity> productsByCategory(Long categoryId) {
        List<ProductsEntity> list = productsRepository.findAllByCategoryId(categoryId);
        return list;
    }

    public List<ProductsEntity> findAllUnion(Long productId) {
        ProductsEntity entity = productsRepository.findById(productId).orElse(null);
        if (entity == null){
            return null;
        }
        List<ProductsEntity> unionProducts = productsRepository.findAllByIdIn(entity.getUnionProducts());
        return unionProducts;
    }

    public List<ProductsEntity> findAllForUnion(String searchText) {
        return productsRepository.findAllForUnion(searchText.toUpperCase());
    }

    public ProductDetailsAdminResponse changeUnion(Long productId, Long unionProductId) {
        ProductsEntity entity = productsRepository.findById(productId).orElse(null);
        if (Arrays.asList(entity.getUnionProducts()).contains(unionProductId)) {
            Long[] unionsArray = new Long[(entity.getUnionProducts().length - 1)];
            int index = 0;
            for (int x = 0; x < entity.getUnionProducts().length; x++){
                if (entity.getUnionProducts()[x] != unionProductId){
                    unionsArray[index] = entity.getUnionProducts()[x];
                    index++;
                }
            }
            entity.setUnionProducts(unionsArray);
        }else {
            Long[] unionsArray = new Long[(entity.getUnionProducts().length + 1)];
            for (int x = 0; x < entity.getUnionProducts().length; x++){
                    unionsArray[x] = entity.getUnionProducts()[x];
            }
            unionsArray[entity.getUnionProducts().length] = unionProductId;
            entity.setUnionProducts(unionsArray);
        }

        productsRepository.save(entity);
        return findDetailsForAdmin(productId);
    }
}
