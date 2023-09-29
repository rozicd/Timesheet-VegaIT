package com.example.vegacalendar.application.controllers;

import com.example.vegacalendar.application.dtos.CreateCountryRequestDTO;
import com.example.vegacalendar.application.dtos.CountryResponseDTO;
import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.model.CountryModel;
import com.example.vegacalendar.core.services.CountryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path="api/country")
public class CountryController {
    @Autowired
    private CountryService countryService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAll(){
        List<CountryModel> countryModels = countryService.getAll();
        List<CountryResponseDTO> countryResponseDTOS = countryModels.stream().map(countryModel -> modelMapper
                .map(countryModel, CountryResponseDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(countryResponseDTOS, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) throws ResourceNotFoundException {
            CountryModel countryModel = countryService.getById(id);
            CountryResponseDTO countryResponseDTO = modelMapper.map(countryModel, CountryResponseDTO.class);
            return  new ResponseEntity<>(countryResponseDTO, HttpStatus.OK);

    }
    @PostMapping()
    public ResponseEntity<?> save(@RequestBody CreateCountryRequestDTO createCountryRequestDTO){
        CountryModel countryModel = modelMapper.map(createCountryRequestDTO, CountryModel.class);
        CountryModel savedCountry = countryService.save(countryModel);
        CountryResponseDTO savedCountryDTO = modelMapper.map(savedCountry, CountryResponseDTO.class);
        return new ResponseEntity<>(savedCountryDTO, HttpStatus.OK);


    }
}
