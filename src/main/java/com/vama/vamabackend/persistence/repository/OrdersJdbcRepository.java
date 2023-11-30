package com.vama.vamabackend.persistence.repository;

import com.vama.vamabackend.persistence.entity.orders.OrderStatusesEnum;
import com.vama.vamabackend.persistence.repository.types.OrdersAdminType;
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

    public List<OrdersAdminType> getAllByForAdmin(String status, String searchText) {
        String sql = "SELECT * FROM getordersforadmin()  where id::text like " + "'%" + searchText + "%'";
        if (status.equals("ACTIVE")){
            sql += " and name = " + "'" + OrderStatusesEnum.ACCEPT.name() + "'" + " or name = " + "'" + OrderStatusesEnum.DELIVERED.name() + "'";
        }else if (status.equals(OrderStatusesEnum.CANCELLED.name())){
            sql += " and name = " + "'" + OrderStatusesEnum.CANCELLED.name() + "'";
        }else if (status.equals(OrderStatusesEnum.COMPLETED.name())){
            sql += " and name = " + "'" + OrderStatusesEnum.COMPLETED.name() + "'";
        }
        List<OrdersAdminType> resultList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrdersAdminType.class));
        return resultList;
    }

    public List<OrdersAdminType> getAllByUserIdForAdmin(Long userId) {
        String sql = "SELECT * FROM getordersforadmin()  where userId = " + userId;
        List<OrdersAdminType> resultList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrdersAdminType.class));
        return resultList;
    }
}
