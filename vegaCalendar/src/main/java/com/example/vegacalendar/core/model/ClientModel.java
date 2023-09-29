package com.example.vegacalendar.core.model;

import java.util.UUID;

public class ClientModel {
    private UUID clientId;
    private String name;
    private String address;
    private String city;
    private String zip;

    private UUID countryId;

    private CountryModel country;
    private boolean deleted = false;
    public ClientModel() {
    }

    public ClientModel(UUID id, String name, String address, String city, String zip, CountryModel countryModel, UUID countryId) {
        this.clientId = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.countryId = countryId;
        this.country = countryModel;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
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

    public CountryModel getCountryModel() {
        return country;
    }

    public void setCountry(CountryModel country) {
        this.country = country;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
