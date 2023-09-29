package com.example.vegacalendar.application.dtos;

import java.util.UUID;

public class ClientResponseDTO {
    private UUID id;
    private String name;
    private String address;
    private String city;
    private String zip;

    private CountryResponseDTO country;

    public ClientResponseDTO() {
    }

    public ClientResponseDTO(UUID id, String name, String address, String city, String zip, CountryResponseDTO country) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.country = country;
    }

    public UUID getClientId() {
        return id;
    }

    public void setClientId(UUID id) {
        this.id = id;
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

    public CountryResponseDTO getCountry() {
        return country;
    }

    public void setCountry(CountryResponseDTO country) {
        this.country = country;
    }
}
