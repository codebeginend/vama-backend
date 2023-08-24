package com.vama.vamabackend.services;

import com.vama.vamabackend.models.account.AccountNameUpdateRequest;
import com.vama.vamabackend.models.account.UserAccountResponse;
import com.vama.vamabackend.persistence.entity.users.UserEntity;
import com.vama.vamabackend.persistence.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserAccountService {

    private UserDetailsServiceImpl userDetailsService;
    private UsersRepository userRepository;

    public UserAccountResponse getUserAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity user = userDetailsService.findByUsername(username);
        UserAccountResponse accountResponse = UserAccountResponse.builder()
                .name(user.getName())
                .logo(user.getLogo())
                .phoneNumber(user.getUsername())
                .email("email@mail.ru")
                .build();
        return accountResponse;
    }

    public UserAccountResponse updateName(AccountNameUpdateRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity user = userDetailsService.findByUsername(username);
        user.setName(request.getName());
        userRepository.save(user);
        return getUserAccount();
    }
}
