package com.example.vegacalendar.application.dtos;

import com.example.vegacalendar.core.model.Status;

import java.util.List;
import java.util.UUID;

public class UpdateProjectRequestDTO {
    private String name;
    private String description;
    private UUID clientId;
    private UUID teamLeadId;

    private List<UUID> teamMembersIDs;
    private Status status;

    public UpdateProjectRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public UUID getTeamLeadId() {
        return teamLeadId;
    }

    public void setTeamLeadId(UUID teamLeadId) {
        this.teamLeadId = teamLeadId;
    }

    public List<UUID> getTeamMembersIDs() {
        return teamMembersIDs;
    }

    public void setTeamMembersIDs(List<UUID> teamMembersIDs) {
        this.teamMembersIDs = teamMembersIDs;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
