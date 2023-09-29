package com.example.vegacalendar.application.dtos;

import java.util.UUID;

public class ChangePasswordDTO {
    private UUID id;
    private String password;

    public ChangePasswordDTO(UUID id, String password) {
        this.id = id;
        this.password = password;
    }

    public ChangePasswordDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
