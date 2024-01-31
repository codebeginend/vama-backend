package com.vama.vamabackend.controllers;

import com.vama.vamabackend.models.verification.VerificationCodeGenerateRequest;
import com.vama.vamabackend.models.verification.VerificationCodeGenerateResponse;
import com.vama.vamabackend.models.verification.VerificationCodesCheckedRequest;
import com.vama.vamabackend.models.verification.VerificationCodesCheckedSuccessResponse;
import com.vama.vamabackend.persistence.entity.auth.VerificationCodesEntity;
import com.vama.vamabackend.services.VerificationCodesService;
import lombok.AllArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("verification/code")
public class VerificationCodesController {

    private VerificationCodesService service;

    @PostMapping(value = "generate")
    public VerificationCodeGenerateResponse phoneRegister(@RequestBody VerificationCodeGenerateRequest request){
        return service.createVerificationCode(request);
    }

    @PostMapping(value = "checked")
    public VerificationCodesCheckedSuccessResponse codeChecked(@RequestBody VerificationCodesCheckedRequest request) throws AuthenticationException {
        return service.checkedCode(request);
    }
}
