package com.example.vegacalendar.services;

import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.model.*;
import com.example.vegacalendar.core.repositories.ActivityRepository;
import com.example.vegacalendar.core.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public ActivityModel save(ActivityModel activityModel) throws ResourceNotFoundException, SQLIntegrityConstraintViolationException {
        return activityRepository.save(activityModel);
    }

    @Override
    public ReportModel getReport(ReportRequestParamsModel reportRequestParamsModel) {
        return activityRepository.getReport(reportRequestParamsModel);
    }

    @Override
    public ActivityPerDayModel getActivityPerDay(UUID id, DateRequestModel dateRequestModel) {
        return activityRepository.getActivityPerDay(id, dateRequestModel);
    }

    @Override
    public DailyActivitiesModel getDailyActivities(UUID id, LocalDate date) {
        return activityRepository.getDailyActivities(id, date);
    }
}
