package com.example.vegacalendar.core.repositories;


import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.model.CountryModel;

import java.util.List;
import java.util.UUID;

public interface CountryRepository {
    List<CountryModel> getAll();
    CountryModel getById(UUID id) throws ResourceNotFoundException;
    CountryModel save(CountryModel countryModel);
}
