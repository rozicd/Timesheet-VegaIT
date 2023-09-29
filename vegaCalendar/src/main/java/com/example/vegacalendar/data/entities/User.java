package com.example.vegacalendar.data.entities;

import com.example.vegacalendar.core.model.Status;
import com.example.vegacalendar.core.model.UserType;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String email;
    @Column
    private UserType userType;
    @Column
    private String password;
    @Column
    private Status status;
    @Column
    private boolean deleted = false;

    public User(UUID id, String name, String surname, String email, UserType userType, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.userType = userType;
        this.status = Status.ACTIVE;
        this.password = password;
    }

    public User(){}

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


    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
