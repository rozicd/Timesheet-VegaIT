package com.example.vegacalendar.core.model;

import java.util.UUID;

public class CountryModel {
    private UUID id;

    private String name;

    private boolean deleted = false;

    public UUID getCountryId() {
        return id;
    }

    public void setCountryId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
