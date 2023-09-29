package com.example.vegacalendar.core.repositories;

import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.model.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.UUID;

public interface ActivityRepository {
    ActivityModel save(ActivityModel activityModel) throws ResourceNotFoundException, SQLIntegrityConstraintViolationException;
    ReportModel getReport(ReportRequestParamsModel reportRequestParamsModel);
    ActivityModel getById(UUID id);

    ActivityPerDayModel getActivityPerDay(UUID id, DateRequestModel dateRequestModel);

    DailyActivitiesModel getDailyActivities(UUID id, LocalDate date);
}
