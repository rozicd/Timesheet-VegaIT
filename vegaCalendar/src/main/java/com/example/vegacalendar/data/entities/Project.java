package com.example.vegacalendar.data.entities;

import com.example.vegacalendar.core.model.Status;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String description;

    @JoinColumn(name = "client_id")
    @ManyToOne(targetEntity = Client.class, fetch = FetchType.EAGER)
    private Client client;
    @JoinColumn(name = "team_lead_id")
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User teamLead;
    @ManyToMany
    private List<User> teamMembers;
    private Status status = Status.ACTIVE;
    private boolean deleted = false;

    public Project() {
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

    public Client getClient() {
        return client;
    }


    public User getTeamLead() {
        return teamLead;
    }

    public void setClient(Client client) {
        this.client = client;
    }



    public void setTeamLead(User teamLead) {
        this.teamLead = teamLead;
    }

    public List<User> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<User> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
