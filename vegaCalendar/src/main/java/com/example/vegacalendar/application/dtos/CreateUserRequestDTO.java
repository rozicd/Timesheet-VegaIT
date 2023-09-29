package com.example.vegacalendar.application.dtos;

import com.example.vegacalendar.core.model.Status;
import com.example.vegacalendar.core.model.UserType;

public class CreateUserRequestDTO {
    private String name;
    private String surname;
    private String email;
    private UserType userType;
    private String password;
    private Status status;
    private String roles;


    public CreateUserRequestDTO() {
    }

    public CreateUserRequestDTO(String name, String surname, String email, UserType userType, String password, Status status) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.userType = userType;
        this.password = password;
        this.status = status;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
