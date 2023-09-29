package com.example.vegacalendar.application.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class ActivityResponseDTO {
    private UUID id;
    private LocalDate date;
    private ProjectResponseDTO project;
    private UserResponseDTO user;
    private CategoryResponseDTO category;
    private ClientResponseDTO client;

    private String description;
    private double time;
    private double overtime;

    public ActivityResponseDTO() {
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

    public ProjectResponseDTO getProject() {
        return project;
    }

    public void setProject(ProjectResponseDTO project) {
        this.project = project;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }

    public CategoryResponseDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryResponseDTO category) {
        this.category = category;
    }

    public ClientResponseDTO getClientResponse() {
        return client;
    }

    public void setClientResponse(ClientResponseDTO client) {
        this.client = client;
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
