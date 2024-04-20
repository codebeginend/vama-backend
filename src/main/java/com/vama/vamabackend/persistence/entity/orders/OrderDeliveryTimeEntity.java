package com.vama.vamabackend.persistence.entity.orders;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "order_delivery_time")
public class OrderDeliveryTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Type(type = "com.vama.vamabackend.persistence.entity.orders.LocalTimeArrayType")
    private LocalTime[][] times;

    @Column(name = " is_delivery")
    private boolean isDelivery;
}
