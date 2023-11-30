package com.vama.vamabackend.persistence.repository;

import com.vama.vamabackend.persistence.repository.types.CategoriesAdminType;
import com.vama.vamabackend.persistence.repository.types.ProductsAdminType;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.beans.BeanProperty;
import java.util.List;

@Repository
@AllArgsConstructor
public class CategoriesJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<CategoriesAdminType> getAllForAdmin(String searchText, Boolean isPublish) {
        searchText = "%" + searchText + "%";
        String sql = "SELECT * FROM getcategoriesforadmin(?)";
        if (isPublish != null){
            sql += " where isPublished = " + isPublish;
        }
        Object[] params = new Object[] { searchText };
        List<CategoriesAdminType> resultList = jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(CategoriesAdminType.class));
        return resultList;
    }

    public CategoriesAdminType getDetailsForAdmin(Long categoryId) {
        String sql = "SELECT * FROM getcategorydetailsforadmin(?)";
        Object[] params = new Object[] { categoryId };
        CategoriesAdminType result = jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(CategoriesAdminType.class));
        return result;
    }
}
