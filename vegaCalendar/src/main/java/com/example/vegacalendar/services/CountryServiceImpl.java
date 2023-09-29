package com.example.vegacalendar.services;

import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.model.CountryModel;
import com.example.vegacalendar.core.repositories.CountryRepository;
import com.example.vegacalendar.core.services.CountryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CountryServiceImpl implements CountryService {
    @Autowired
    private CountryRepository countryRepository;
    @Override
    public List<CountryModel> getAll() {

        return countryRepository.getAll();
    }

    @Override
    public CountryModel getById(UUID id) throws ResourceNotFoundException {
        return countryRepository.getById(id);
    }

    @Override
    public CountryModel save(CountryModel countryModel) {

        return countryRepository.save(countryModel);
    }
}
