package com.vama.vamabackend.persistence.repository;

import com.vama.vamabackend.persistence.entity.categories.CategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepository extends JpaRepository<CategoriesEntity, Long> {

    @Query("select c from CategoriesEntity c where c.parentId is null and c.isPublished is true")
    List<CategoriesEntity> findAllByParentIdIsNull();

    @Query("select c from CategoriesEntity c where c.parentId = :id ")
    List<CategoriesEntity> findAllByParentId(Integer id);
}
