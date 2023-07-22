package com.vama.vamabackend.persistence.entity.users;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "roles")
public class RoleEntity implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RolesEnum name;


    @OneToMany(mappedBy = "role")
    private List<UserEntity> users  = new ArrayList<>();;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RolesEnum getName() {
        return name;
    }

    public void setName(RolesEnum name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name.name();
    }

    public RoleEntity() {
    }

    public RoleEntity(RolesEnum name) {
        this.name = name;
    }
}