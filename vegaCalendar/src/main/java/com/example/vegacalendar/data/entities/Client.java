package com.example.vegacalendar.data.entities;

import jakarta.persistence.*;

import java.util.UUID;
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column
    private String name;
    @Column
    private String address;
    @Column
    private String city;
    @Column
    private String zip;
    @JoinColumn(name = "country_id")
    @ManyToOne(targetEntity = Country.class, fetch = FetchType.EAGER)
    private Country country;
    @Column
    private boolean deleted;

    public Client() {
    }

    public Client(UUID id, String name, String address, String city, String zip, Country country) {
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country countryId) {
        this.country = countryId;
    }


    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
