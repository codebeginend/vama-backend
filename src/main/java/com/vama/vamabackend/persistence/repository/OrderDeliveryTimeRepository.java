package com.vama.vamabackend.persistence.repository;

import com.vama.vamabackend.persistence.entity.orders.OrderDeliveryTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface OrderDeliveryTimeRepository extends JpaRepository<OrderDeliveryTimeEntity, Long> {

    @Query("select time from OrderDeliveryTimeEntity time order by time.id asc")
    List<OrderDeliveryTimeEntity> findAll();
    OrderDeliveryTimeEntity findByDayOfWeek(DayOfWeek dayOfWeek);
    @Query("select time from OrderDeliveryTimeEntity time where time.isDelivery is true order by time.id asc")
    List<OrderDeliveryTimeEntity> findAllByDeliveryIsTrue();
}
