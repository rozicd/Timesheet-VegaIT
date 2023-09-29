package com.example.vegacalendar.core.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class UserInfo implements UserDetails {
    private UUID id;
    private String username;
    private String name;
    private String password;
    private List<GrantedAuthority> authorities = new ArrayList<>();

    public UserInfo(UserModel userModel) {
        id = userModel.getUserId();
        username = userModel.getEmail();
        name = userModel.getName();
        password = userModel.getPassword();
        GrantedAuthority authority = new SimpleGrantedAuthority(userModel.getUserType().toString());
        authorities.add(authority);

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
