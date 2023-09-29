package com.example.vegacalendar.core.services;

import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.model.CountryModel;

import java.util.List;
import java.util.UUID;

public interface CountryService {
    List<CountryModel> getAll();
    CountryModel getById(UUID id) throws ResourceNotFoundException;
    CountryModel save(CountryModel countryModel);
}
