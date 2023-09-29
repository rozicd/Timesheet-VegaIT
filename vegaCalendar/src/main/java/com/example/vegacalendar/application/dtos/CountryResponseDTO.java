package com.example.vegacalendar.application.dtos;

import java.util.UUID;

public class CountryResponseDTO {
    private UUID id;

    private String name;

    public CountryResponseDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
