package com.vama.vamabackend.persistence.repository;

import com.vama.vamabackend.persistence.entity.auth.VerificationCodesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationCodesRepository extends JpaRepository<VerificationCodesEntity, Long> {
    @Query("SELECT vc FROM VerificationCodesEntity vc where vc.id = (select max(svc.id) from VerificationCodesEntity svc where svc.phoneNumber = :phone) ")
    VerificationCodesEntity findLastByPhoneNumber(String phone);
}
