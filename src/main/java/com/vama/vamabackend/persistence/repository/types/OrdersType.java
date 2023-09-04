package com.vama.vamabackend.persistence.repository.types;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrdersType {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
}
