package com.example.vegacalendar.application.dtos;

import com.example.vegacalendar.core.model.Status;
import com.example.vegacalendar.core.model.UserType;

public class UpdateUserRequestDTO {
    private String name;

    private String surname;
    private String email;
    private UserType userType;
    private Status status;

    public UpdateUserRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
