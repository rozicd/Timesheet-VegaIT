package com.example.vegacalendar.core.model;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class ActivityModel {
    private UUID id;
    private LocalDate date = LocalDate.now();
    private ProjectModel project;
    private UserModel user;
    private ClientModel client;
    private CategoryModel categoryModel;
    private String description;
    private double time;
    private double overtime;

    public ActivityModel() {
    }

    public ClientModel getClient() {
        return client;
    }

    public void setClient(ClientModel client) {
        this.client = client;
    }
    public UUID getActivitysId() {
        return id;
    }

    public void setActivitysId(UUID id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ProjectModel getProject() {
        return project;
    }

    public void setProject(ProjectModel project) {
        this.project = project;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public CategoryModel getCategory() {
        return categoryModel;
    }

    public void setCategory(CategoryModel categoryModel) {
        this.categoryModel = categoryModel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getOvertime() {
        return overtime;
    }

    public void setOvertime(double overtime) {
        this.overtime = overtime;
    }
}
