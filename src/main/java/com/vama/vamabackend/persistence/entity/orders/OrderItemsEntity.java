package com.vama.vamabackend.persistence.entity.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "order_items")
@AllArgsConstructor()
@NoArgsConstructor
public class OrderItemsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "count")
    private BigDecimal count;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "order_id")
    private Long orderId;

    public OrderItemsEntity(BigDecimal count, Long productId, Long orderId, BigDecimal price, BigDecimal discount){
        this.count = count;
        this.productId = productId;
        this.orderId = orderId;
        this.price = price;
        this.discount =discount;
    }
}
