package com.vama.vamabackend.persistence.repository;

import com.vama.vamabackend.persistence.entity.orders.OrderStatusesEnum;
import com.vama.vamabackend.persistence.repository.types.OrdersAdminType;
import com.vama.vamabackend.persistence.repository.types.OrdersType;
import com.vama.vamabackend.persistence.repository.types.UsersAdminType;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class UsersJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<UsersAdminType> getAllForAdmin(String searchText) {
        String sql = "SELECT * FROM getusersforadmin()  where name ilike " + "'%" + searchText + "%'"  + "or phoneNumber ilike '%" + searchText + "%'" ;
        List<UsersAdminType> resultList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UsersAdminType.class));
        return resultList;
    }
}
