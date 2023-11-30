package com.vama.vamabackend.persistence.entity.orders;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "orders")
public class OrdersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "address")
    private String address;

    @Column(name = "entrance")
    private String entrance;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_type")
    private DeliveryTypeEnum deliveryType;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentTypeEnum paymentType;

    @Column(name = "payment_status")
    private String paymentStatus;
}
