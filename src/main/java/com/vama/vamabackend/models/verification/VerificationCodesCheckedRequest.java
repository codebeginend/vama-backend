package com.vama.vamabackend.models.verification;

import lombok.Data;

@Data
public class VerificationCodesCheckedRequest {
    private String phoneNumber;
    private String code;
}
