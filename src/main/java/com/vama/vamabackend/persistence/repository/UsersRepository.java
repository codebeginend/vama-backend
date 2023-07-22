package com.vama.vamabackend.persistence.repository;

import com.vama.vamabackend.persistence.entity.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
