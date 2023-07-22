package com.vama.vamabackend.models.verification;

import lombok.Data;

@Data
public class VerificationCodesCheckedSuccessResponse {
    private String jwtToken;
    private boolean isNewUser;
}
