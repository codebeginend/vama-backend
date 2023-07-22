package com.vama.vamabackend.models.verification;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VerificationCodeGenerateResponse {
    private String phoneNumber;
    private LocalDateTime expiresAt;
}
