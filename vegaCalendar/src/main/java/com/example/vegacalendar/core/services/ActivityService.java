package com.example.vegacalendar.core.services;

import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.model.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.UUID;

public interface ActivityService {
    ActivityModel save(ActivityModel activityModel) throws ResourceNotFoundException, SQLIntegrityConstraintViolationException;
    ReportModel getReport(ReportRequestParamsModel reportRequestParamsModel);
    ActivityPerDayModel getActivityPerDay(UUID id, DateRequestModel dateRequestModel);
    DailyActivitiesModel getDailyActivities(UUID id, LocalDate date);
}
