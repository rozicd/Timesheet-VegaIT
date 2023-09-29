package com.example.vegacalendar.application.dtos;

import com.example.vegacalendar.core.model.Status;
import com.example.vegacalendar.core.model.UserModel;
import com.example.vegacalendar.core.model.UserType;

import java.util.List;
import java.util.UUID;

public class UserResponseDTO {
    private UUID id;
    private String name;

    private String surname;
    private String email;
    private UserType userType;
    private Status status;


    public UserResponseDTO() {
    }

    public UserResponseDTO(UUID id, String name, String surname, String email, UserType userType, Status status) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.userType = userType;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
