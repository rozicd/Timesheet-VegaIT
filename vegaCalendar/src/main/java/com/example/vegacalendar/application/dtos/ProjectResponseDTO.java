package com.example.vegacalendar.application.dtos;

import com.example.vegacalendar.core.model.Status;

import java.util.List;
import java.util.UUID;

public class ProjectResponseDTO {
    private UUID id;
    private String name;
    private String description;
    private ClientResponseDTO client;
    private UserResponseDTO teamLead;
    private List<UserResponseDTO> teamMembers;

    private Status status = Status.ACTIVE;

    public ProjectResponseDTO() {
    }

    public UUID getProjectId() {
        return id;
    }

    public void setProjectId(UUID id) {
        this.id = id;
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

    public ClientResponseDTO getClient() {
        return client;
    }

    public void setClient(ClientResponseDTO client) {
        this.client = client;
    }

    public UserResponseDTO getTeamLead() {
        return teamLead;
    }

    public void setTeamLead(UserResponseDTO teamLead) {
        this.teamLead = teamLead;
    }

    public List<UserResponseDTO> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<UserResponseDTO> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
