package com.example.vegacalendar.core.model;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class LoggedUser {
    private UUID id;
    private String email;
    private UserType role;

    public LoggedUser(UUID id, String email, UserType role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public LoggedUser() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getRole() {
        return role;
    }

    public void setRole(UserType role) {
        this.role = role;
    }

}
