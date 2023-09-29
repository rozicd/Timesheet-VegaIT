package com.example.vegacalendar.data.jpas;

import com.example.vegacalendar.data.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ProjectJPARepository extends CrudRepository<Project, UUID> {
    Page<Project> findByNameStartingWithAndNameStartingWithAndDeletedFalse(String firstLetter, String search, Pageable pageable);

    @Query("SELECT p FROM Project p WHERE p.teamLead.id = :teamLeadId AND (p.name LIKE CONCAT(:nameStartWith, '%') OR p.name LIKE CONCAT(:nameStartWithSearch, '%')) AND p.deleted = false")
    Page<Project> findProjectsByTeamLeadIdAndFilter(
            @Param("teamLeadId") UUID teamLeadId,
            @Param("nameStartWith") String nameStartWith,
            @Param("nameStartWithSearch") String nameStartWithSearch,
            Pageable pageable
    );

}
