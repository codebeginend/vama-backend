package com.vama.vamabackend.models.account;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAccountResponse {
    private String logo;
    private String name;
    private String phoneNumber;
    private String email;
}
