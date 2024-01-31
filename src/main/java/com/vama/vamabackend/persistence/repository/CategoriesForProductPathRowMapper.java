package com.vama.vamabackend.persistence.repository;

import com.vama.vamabackend.models.categories.CategoriesForProduct;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoriesForProductPathRowMapper implements RowMapper<CategoriesForProduct> {
    @Override
    public CategoriesForProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
        CategoriesForProduct categoryPath = new CategoriesForProduct();
        categoryPath.setIdOne(rs.getLong("id_one"));
        categoryPath.setNameOne(rs.getString("name_one"));
        categoryPath.setParentIdOne(rs.getInt("parent_id_one"));
        categoryPath.setIdTwo(rs.getLong("id_two"));
        categoryPath.setNameTwo(rs.getString("name_two"));
        categoryPath.setParentIdTwo(rs.getInt("parent_id_two"));
        categoryPath.setIdThree(rs.getLong("id_three"));
        categoryPath.setNameThree(rs.getString("name_three"));
        categoryPath.setParentIdThree(rs.getInt("parent_id_three"));
        return categoryPath;
    }
}