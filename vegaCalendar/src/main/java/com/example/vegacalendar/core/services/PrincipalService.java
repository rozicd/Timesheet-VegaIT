package com.example.vegacalendar.core.services;

import com.example.vegacalendar.core.model.UserType;

import java.util.UUID;

public interface PrincipalService {
    UUID getId();
    String getEmail();
    UserType getRole();
}
