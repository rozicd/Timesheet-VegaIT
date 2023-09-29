package com.example.vegacalendar.services;

import com.example.vegacalendar.core.model.TokenPrincipalModel;
import com.example.vegacalendar.core.model.UserType;
import com.example.vegacalendar.core.services.PrincipalService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PrincipalServiceImpl implements PrincipalService {

    @Override
    public UUID getId() {
        TokenPrincipalModel principal = (TokenPrincipalModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getId();
    }

    @Override
    public String getEmail() {
        TokenPrincipalModel principal = (TokenPrincipalModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getEmail();
    }
    @Override
    public UserType getRole() {
        TokenPrincipalModel principal = (TokenPrincipalModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getRole();
    }
}
