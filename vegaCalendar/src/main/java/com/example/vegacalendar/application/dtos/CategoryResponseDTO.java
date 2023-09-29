package com.example.vegacalendar.application.dtos;

import java.util.UUID;

public class CategoryResponseDTO {
    private UUID id;

    private String name;

    public CategoryResponseDTO() {
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
