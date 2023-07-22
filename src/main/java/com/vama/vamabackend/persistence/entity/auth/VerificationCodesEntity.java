package com.vama.vamabackend.persistence.entity.auth;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vama.vamabackend.persistence.entity.serialization.LocalDateTimeDeserializer;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "verification_codes")
public class VerificationCodesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "code")
    private String code;
    @Column(name = "is_used", columnDefinition = "boolean default false")
    private boolean isUsed = false;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}