package com.vama.vamabackend.persistence.repository;

import com.vama.vamabackend.persistence.repository.types.OrdersType;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class OrdersJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<OrdersType> getAllByUserId(Long userId) {
        String sql = "SELECT * FROM getordersbyuserid(?)";
        Object[] params = new Object[] { userId };
        List<OrdersType> resultList = jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(OrdersType.class));
        return resultList;
    }
}
