package com.example.vegacalendar.core.model;

import java.util.List;
import java.util.UUID;

public class ProjectModel {
    private UUID id;
    private String name;
    private String description;

    private UUID clientId;
    private ClientModel client;
    private UUID teamLeadId;
    private UserModel teamLead;
    private List<UUID> teamMembersIDs;
    private List<UserModel> teamMembers;
    private Status status = Status.ACTIVE;

    public ProjectModel() {
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

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientModelId) {
        this.clientId = clientModelId;
    }

    public ClientModel getCLient() {
        return client;
    }

    public void setClient(ClientModel client) {
        this.client = client;
    }

    public UserModel getTeamLead() {
        return teamLead;
    }

    public void setTeamLead(UserModel teamLead) {
        this.teamLead = teamLead;
    }

    public UUID getTeamLeadId() {
        return teamLeadId;
    }

    public void setTeamLeadId(UUID teamLeadId) {
        this.teamLeadId = teamLeadId;
    }

    public UserModel getUser() {
        return teamLead;
    }

    public void setUser(UserModel teamLead) {
        this.teamLead = teamLead;
    }

    public List<UUID> getTeamMembersIDs() {
        return teamMembersIDs;
    }

    public void setTeamMembersIDs(List<UUID> teamMembersIDs) {
        this.teamMembersIDs = teamMembersIDs;
    }

    public List<UserModel> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<UserModel> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
