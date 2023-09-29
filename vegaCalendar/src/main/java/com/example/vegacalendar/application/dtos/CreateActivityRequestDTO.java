package com.example.vegacalendar.application.dtos;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class CreateActivityRequestDTO {
    private UUID clientId;
    private UUID projectId;
    private UUID categoryId;

    private String description;
    private double time;
    private double overtime;

    public CreateActivityRequestDTO() {}

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
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
