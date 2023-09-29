package com.example.vegacalendar.application.dtos;

import java.util.UUID;

public class CreateClientRequestDTO {
    private String name;
    private String address;
    private String city;
    private String zip;
    private UUID countryId;

    public CreateClientRequestDTO() {
    }

    public CreateClientRequestDTO(String name, String address, String city, String zip, UUID countryId) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public UUID getCountryId() {
        return countryId;
    }

    public void setCountryId(UUID countryId) {
        this.countryId = countryId;
    }
}
