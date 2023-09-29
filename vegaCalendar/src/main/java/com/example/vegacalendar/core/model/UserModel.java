package com.example.vegacalendar.core.model;



import com.example.vegacalendar.data.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

public class UserModel {
    private UUID id;
    private String name;

    private String surname;
    private String email;
    private UserType userType;
    private String password;
    private Status status;

    public UserModel() {
    }

    public UserModel(UUID id, String name, String surname, String email, UserType userType, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.userType = userType;
        this.password = password;
        this.status = Status.ACTIVE;
    }

    public UserModel(UUID id, String name, String surname, String email, UserType userType, String password, Status status) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.userType = userType;
        this.password = password;
        this.status = status;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }

    public UUID getUserId() {
        return id;
    }

    public void setUserId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


}
