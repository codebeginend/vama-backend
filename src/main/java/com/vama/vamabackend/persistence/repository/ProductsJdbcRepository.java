package com.vama.vamabackend.persistence.repository;

import com.vama.vamabackend.persistence.repository.types.ProductsAdminType;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ProductsJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<ProductsAdminType> getAllForAdmin(String searchText, Boolean isPublish) {
        searchText = "%" + searchText + "%";
        String sql = "SELECT * FROM getproductsforadmin(?)";
        if (isPublish != null){
            sql += " where isPublished = " + isPublish;
        }
        Object[] params = new Object[] { searchText };
        List<ProductsAdminType> resultList = jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(ProductsAdminType.class));
        return resultList;
    }
}
