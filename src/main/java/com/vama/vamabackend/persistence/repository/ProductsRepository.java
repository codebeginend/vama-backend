package com.vama.vamabackend.persistence.repository;

import com.vama.vamabackend.persistence.entity.products.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<ProductsEntity, Long> {

    List<ProductsEntity> findAllByCategoryId(Long categoryId);
    @Query("select name from ProductsEntity where UPPER(name) LIKE :searchText")
    List<String> findAllLike(String searchText);

    @Query("select product from ProductsEntity product where UPPER(product.name) LIKE :name")
    List<ProductsEntity> findAllByName(String name);

    @Query("select product from ProductsEntity product where UPPER(product.code) LIKE :code")
    ProductsEntity findAllByCode(String code);

    @Query("select product from ProductsEntity product where product.isPopular = true")
    List<ProductsEntity> findAllByPopularIsTrue();

    @Query("select product from ProductsEntity product where (product.categoryId = :categoryId or product.categoryId is null) and upper(product.name) like %:searchText%")
    List<ProductsEntity> findAllByCategoryIdOrCategoryIdIsNullAndNameLike(Long categoryId, String searchText);

    List<ProductsEntity> findAllByIdIn(Long[] unionProducts);

    @Query("select product from ProductsEntity product where upper(product.name) like %:searchText%")
    List<ProductsEntity> findAllForUnion(String searchText);

}
