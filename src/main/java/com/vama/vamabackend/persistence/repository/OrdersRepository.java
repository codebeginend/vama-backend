package com.vama.vamabackend.persistence.repository;

import com.vama.vamabackend.persistence.entity.orders.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Long> {

    @Query("select order from OrdersEntity order where order.id = (select max(subOrder.id) from OrdersEntity subOrder where subOrder.userId = :userId)")
    OrdersEntity findLastByUserId(Long userId);
}
