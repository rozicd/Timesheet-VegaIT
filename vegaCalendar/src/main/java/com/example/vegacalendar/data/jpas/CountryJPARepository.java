package com.example.vegacalendar.data.jpas;

import com.example.vegacalendar.data.entities.Country;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface CountryJPARepository extends CrudRepository<Country, UUID> {

    Optional<Country> findCountryByIdAndDeletedFalse(UUID id);
    Iterable<Country> findAllByDeletedFalse();
}
