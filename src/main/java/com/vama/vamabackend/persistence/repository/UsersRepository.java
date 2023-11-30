package com.vama.vamabackend.persistence.repository;

import com.vama.vamabackend.persistence.entity.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);

    @Query("select u from UserEntity u left join RoleEntity r on u.roleId = r.id where u.id = :id and r.name = 'ROLE_USER'")
    UserEntity findByClient(Long id);
}
