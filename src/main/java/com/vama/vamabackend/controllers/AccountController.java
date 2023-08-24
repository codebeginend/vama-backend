package com.vama.vamabackend.controllers;

import com.vama.vamabackend.models.account.AccountNameUpdateRequest;
import com.vama.vamabackend.models.account.UserAccountResponse;
import com.vama.vamabackend.services.UserAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("account")
@AllArgsConstructor
public class AccountController {
    private UserAccountService accountService;

    @GetMapping(value = "me")
    private UserAccountResponse getAccount() {
        return accountService.getUserAccount();
    }

    @PostMapping(value = "update/name")
    private UserAccountResponse updateName(@RequestBody AccountNameUpdateRequest request){
        return accountService.updateName(request);
    }
}
