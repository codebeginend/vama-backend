package com.vama.vamabackend.persistence.repository;

import com.vama.vamabackend.persistence.entity.orders.OrderStatusesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderStatusesRepository extends JpaRepository<OrderStatusesEntity, Long> {

    @Query("select status from OrderStatusesEntity status where status.orderId = :orderId and " +
            "status.createdAt = (select MAX(subStatus.createdAt) from OrderStatusesEntity subStatus where subStatus.orderId = :orderId)")
    OrderStatusesEntity findLastByOrderId(Long orderId);
}
