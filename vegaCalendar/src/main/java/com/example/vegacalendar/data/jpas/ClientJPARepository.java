package com.example.vegacalendar.data.jpas;

import com.example.vegacalendar.data.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientJPARepository extends JpaRepository<Client, UUID> {
    Page<Client> findClientsByNameStartingWithAndDeletedFalse(String letter, Pageable pageable);
    Optional<Client> findClientByIdAndDeletedFalse(UUID id);
    List<Client> findAllByDeletedFalse();
    Page<Client> findByNameStartingWithAndNameStartingWithAndDeletedFalse(String firstLetter, String search, Pageable pageable);
}
