package com.vama.vamabackend.models.clients;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ClientsAdminResponse {
    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private Long orderCount;
    private BigDecimal totalSum;
}
