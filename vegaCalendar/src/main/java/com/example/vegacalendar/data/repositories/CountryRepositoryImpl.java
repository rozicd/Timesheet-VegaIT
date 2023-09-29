package com.example.vegacalendar.data.repositories;

import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.model.CountryModel;
import com.example.vegacalendar.core.repositories.CountryRepository;
import com.example.vegacalendar.data.entities.Country;
import com.example.vegacalendar.data.jpas.CountryJPARepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class CountryRepositoryImpl implements CountryRepository {
    @Autowired
    CountryJPARepository countryJPARepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<CountryModel> getAll() {
        Iterable<Country> countries= countryJPARepository.findAllByDeletedFalse();
        List<CountryModel> countryModels = StreamSupport.stream(countries.spliterator(),false)
                .map(country -> modelMapper.map(country, CountryModel.class)).collect(Collectors.toList());

        return countryModels;
    }

    @Override
    public CountryModel getById(UUID id) throws ResourceNotFoundException {
        Optional<Country> country= countryJPARepository.findCountryByIdAndDeletedFalse(id);
        if(country.isEmpty()){
            throw new ResourceNotFoundException("Country with that id:" + id + " does not exist");
        }
        return modelMapper.map(country, CountryModel.class);

    }

    @Override
    public CountryModel save(CountryModel countryModel) {
        Country country = modelMapper.map(countryModel, Country.class);
        countryJPARepository.save(country);
        return modelMapper.map(country, CountryModel.class);
    }
}
