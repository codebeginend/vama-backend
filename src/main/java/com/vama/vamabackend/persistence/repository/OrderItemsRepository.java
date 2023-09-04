package com.vama.vamabackend.persistence.repository;

import com.vama.vamabackend.persistence.entity.orders.OrderItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItemsEntity, Long> {

    List<OrderItemsEntity> findAllByOrderId(Long orderId);
}
