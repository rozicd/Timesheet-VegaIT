package com.example.vegacalendar.application.dtos;

import com.example.vegacalendar.application.Constants;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class ReportRequestParamsDTO {
    private UUID teamMemberId;
    private UUID clientId;

    private UUID categoryId;

    private UUID projectId;
    private LocalDate startDate;
    private LocalDate endDate;

    @ConstructorProperties({"teamMemberId","clientId", "categoryId", "projectId", "startDate", "endDate"})
    public ReportRequestParamsDTO(UUID teamMemberId, UUID clientId, UUID categoryId, UUID projectId, LocalDate startDate, LocalDate endDate) {
        this.teamMemberId = teamMemberId;
        this.clientId = clientId ;
        this.categoryId = categoryId ;
        this.projectId = projectId ;
        this.startDate = startDate ;
        this.endDate = endDate ;
    }

    public UUID getTeamMemberId() {
        return teamMemberId;
    }

    public void setTeamMemberId(UUID teamMemberId) {
        this.teamMemberId = teamMemberId;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}

