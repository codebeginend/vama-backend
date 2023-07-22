package com.vama.vamabackend.models.verification;

import lombok.Data;

@Data
public class VerificationCodeGenerateRequest {
    private String phoneNumber;
}
