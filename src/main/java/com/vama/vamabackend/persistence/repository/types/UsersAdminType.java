package com.vama.vamabackend.persistence.repository.types;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UsersAdminType {
    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private Long orderCount;
    private BigDecimal totalSum;
}
