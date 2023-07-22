package com.vama.vamabackend.services;

import com.vama.vamabackend.persistence.entity.users.UserEntity;
import com.vama.vamabackend.persistence.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserEntity user = this.userRepository.findByUsername(login);
        return new UserDetails(){
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return new ArrayList<GrantedAuthority>(Collections.singleton(new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return user.getRole().getName().name();
                    }
                }));
            }

            @Override
            public String getPassword() {
                return user.getPassword();
            }

            @Override
            public String getUsername() {
                return user.getUsername();
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }
        };
    }

    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}