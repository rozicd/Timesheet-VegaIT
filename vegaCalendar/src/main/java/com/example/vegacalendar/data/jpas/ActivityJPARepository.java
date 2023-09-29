package com.example.vegacalendar.data.jpas;

import com.example.vegacalendar.application.dtos.ReportRequestParamsDTO;
import com.example.vegacalendar.core.model.ReportRequestParamsModel;
import com.example.vegacalendar.data.entities.Activity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ActivityJPARepository extends CrudRepository<Activity, UUID> {
    @Query("SELECT a FROM Activity a WHERE " +
            "(:userId IS NULL OR a.user.id = :userId) " +
            "AND (:clientId IS NULL OR a.project.client.id = :clientId) " +
            "AND (:categoryId IS NULL OR a.category.id = :categoryId) " +
            "AND (:projectId IS NULL OR a.project.id = :projectId) " +
            "AND (:start IS NULL OR a.date >= :start) " +
            "AND (:end IS NULL OR a.date <= :end)")
    Iterable<Activity> findAllFiltered(
            @Param("userId") UUID userId,
            @Param("clientId") UUID clientId,
            @Param("categoryId") UUID categoryId,
            @Param("projectId") UUID projectId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );
    List<Activity> findActivitiesByUserIdAndDateBetween(UUID id, LocalDate start, LocalDate end);

    List<Activity> findActivitiesByUserIdAndDate(UUID id, LocalDate date);
}
