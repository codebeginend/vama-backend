package com.vama.vamabackend.persistence.repository;

import com.vama.vamabackend.models.categories.CategoriesForProduct;
import com.vama.vamabackend.persistence.repository.types.CategoriesAdminType;
import com.vama.vamabackend.persistence.repository.types.ProductsAdminType;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.beans.BeanProperty;
import java.sql.ResultSet;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<CategoriesForProduct> getAllForProduct(List<Long> ids) {
        String idsString = ids.stream().map(i -> i.toString()).collect(Collectors.joining(","));
        String sql = "WITH RECURSIVE category_path AS (\n" +
                "    SELECT id, name, parent_id\n" +
                "    FROM categories\n" +
                "    WHERE id in (" + idsString + ")"+
                "    UNION\n" +
                "    SELECT c.id, c.name, c.parent_id\n" +
                "    FROM categories c\n" +
                "    JOIN category_path cp ON c.id = cp.parent_id\n" +
                ")\n" +
                "SELECT cp1.id as id_one, cp1.name as name_one, cp1.parent_id as parent_id_one, \n" +
                "       cp2.id as id_two, cp2.name as name_two, cp2.parent_id as parent_id_two, \n" +
                "       cp3.id as id_three, cp3.name as name_three, cp3.parent_id as parent_id_three\n" +
                "FROM category_path cp1\n" +
                "LEFT JOIN category_path cp2 ON cp1.parent_id = cp2.id\n" +
                "LEFT JOIN category_path cp3 ON cp2.parent_id = cp3.id\n" +
                "WHERE cp1.id in (" + idsString + ")";

        List<CategoriesForProduct> resultList = jdbcTemplate.query(sql, new CategoriesForProductPathRowMapper());
        return resultList;
    }
}
