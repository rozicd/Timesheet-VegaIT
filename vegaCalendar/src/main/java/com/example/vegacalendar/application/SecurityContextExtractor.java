package com.example.vegacalendar.application;

import com.example.vegacalendar.core.model.LoggedUser;
import com.example.vegacalendar.core.model.TokenPrincipalModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class SecurityContextExtractor {
    public LoggedUser getLoggedUser(){
        TokenPrincipalModel principal = (TokenPrincipalModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new LoggedUser(principal.getId(), principal.getEmail(), principal.getRole());
    }
}
