package com.example.dimondinvest.security;

import com.example.dimondinvest.entity.Register;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserPrincipal implements UserDetails {

    private Register register;

    public UserPrincipal(Register register) {
        this.register = register;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_"+register.getRoles().getRolename().toUpperCase()));
    }

    @Override
    public String getPassword() {
        return register.getPassword();
    }

    @Override
    public String getUsername() {
        return register.getNumber().toString();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
