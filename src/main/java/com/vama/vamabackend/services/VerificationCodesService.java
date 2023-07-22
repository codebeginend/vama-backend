package com.vama.vamabackend.services;

import com.vama.vamabackend.models.verification.VerificationCodeGenerateRequest;
import com.vama.vamabackend.models.verification.VerificationCodeGenerateResponse;
import com.vama.vamabackend.models.verification.VerificationCodesCheckedRequest;
import com.vama.vamabackend.models.verification.VerificationCodesCheckedSuccessResponse;
import com.vama.vamabackend.persistence.entity.auth.VerificationCodesEntity;
import com.vama.vamabackend.persistence.entity.users.RoleEntity;
import com.vama.vamabackend.persistence.entity.users.UserEntity;
import com.vama.vamabackend.persistence.repository.RolesRepository;
import com.vama.vamabackend.persistence.repository.UsersRepository;
import com.vama.vamabackend.persistence.repository.VerificationCodesRepository;
import com.vama.vamabackend.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
@AllArgsConstructor
public class VerificationCodesService {

    private VerificationCodesRepository repository;
    private UsersRepository usersRepository;
    private RolesRepository rolesRepository;
    private JwtTokenProvider jwtTokenProvider;

    public VerificationCodeGenerateResponse createVerificationCode(VerificationCodeGenerateRequest request){
        String randomCode = generateRandomCode();
        VerificationCodesEntity entity = new VerificationCodesEntity();
        entity.setPhoneNumber(request.getPhoneNumber());
        entity.setCode(randomCode);
        entity.setExpiresAt(LocalDateTime.now().plusMinutes(1));
        repository.save(entity);
        VerificationCodeGenerateResponse response = new VerificationCodeGenerateResponse();
        response.setPhoneNumber(request.getPhoneNumber());
        response.setExpiresAt(entity.getExpiresAt());
        return response;
    }

    public VerificationCodesCheckedSuccessResponse checkedCode(VerificationCodesCheckedRequest request) throws AuthenticationException {
        VerificationCodesEntity lastCode = repository.findLastByPhoneNumber(request.getPhoneNumber());
        if (lastCode == null){
            throw new AuthenticationCredentialsNotFoundException("Dont checked code");
        }
        if (!lastCode.getCode().equals(request.getCode())){
            throw new AuthenticationCredentialsNotFoundException("Dont checked code");
        }
        if (!lastCode.getExpiresAt().isAfter(LocalDateTime.now())){
            throw new AuthenticationCredentialsNotFoundException("Time expired");
        }
        if (lastCode.isUsed()){
            throw new AuthenticationCredentialsNotFoundException("Error: code used");
        }
        lastCode.setUsed(true);
        repository.save(lastCode);
        VerificationCodesCheckedSuccessResponse response = new VerificationCodesCheckedSuccessResponse();
        UserEntity userEntity = usersRepository.findByUsername(request.getPhoneNumber());
        if (userEntity == null){
            response.setNewUser(true);
            userEntity = new UserEntity();
            userEntity.setName("Пользователь");
            userEntity.setUsername(request.getPhoneNumber());
            userEntity.setRoleId(2L);
            userEntity = usersRepository.save(userEntity);
        }
        RoleEntity role = rolesRepository.findById(userEntity.getRoleId()).orElse(null);
        String jwtToken = jwtTokenProvider.createToken(userEntity.getUsername(), role.getName());
        response.setJwtToken(jwtToken);
        return response;
    }


    public static String generateRandomCode() {
        int min = 1000;
        int max = 9999;
        // Генерируем случайное число в диапазоне от min до max
        int randomNumber = ThreadLocalRandom.current().nextInt(min, max + 1);
        // Преобразуем случайное число в строку с 4 знаками (если число меньше 1000, добавляем ведущий ноль)
        return String.format("%04d", randomNumber);
    }
}
