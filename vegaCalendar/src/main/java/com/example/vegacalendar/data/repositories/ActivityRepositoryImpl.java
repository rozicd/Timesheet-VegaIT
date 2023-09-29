package com.example.vegacalendar.data.repositories;

import com.example.vegacalendar.core.model.*;
import com.example.vegacalendar.core.repositories.*;
import com.example.vegacalendar.data.entities.Activity;
import com.example.vegacalendar.data.jpas.ActivityJPARepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class ActivityRepositoryImpl implements ActivityRepository {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ActivityJPARepository activityJPARepository;

    @Override
    public ActivityModel save(ActivityModel activityModel) throws SQLIntegrityConstraintViolationException {
        Activity activity = modelMapper.map(activityModel, Activity.class);
        activityJPARepository.save(activity);
        return modelMapper.map(activity, ActivityModel.class);
    }

    @Override
    public ReportModel getReport(ReportRequestParamsModel reportRequestParamsModel) {

        Iterable<Activity> activities = activityJPARepository.findAllFiltered(
                reportRequestParamsModel.getTeamMemberId(),
                reportRequestParamsModel.getClientId(),
                reportRequestParamsModel.getCategoryId(),
                reportRequestParamsModel.getProjectId(),
                reportRequestParamsModel.getStartDate(),
                reportRequestParamsModel.getEndDate());
        List<ActivityModel> activityModels = StreamSupport.stream(activities.spliterator(), false)
                .map(activity -> modelMapper.map(activity,ActivityModel.class)).collect(Collectors.toList());
        ReportModel report = new ReportModel(activityModels);
        activityModels.forEach(activityModel -> {
            report.increaseHours(activityModel.getTime(), activityModel.getOvertime());
        });
        return report;
    }

    @Override
    public ActivityModel getById(UUID id){
        Optional<Activity> activity = activityJPARepository.findById(id);
        return modelMapper.map(activity, ActivityModel.class);
    }
    @Override
    public ActivityPerDayModel getActivityPerDay(UUID id, DateRequestModel dateRequestModel) {
        List<Activity> userActivities= activityJPARepository.findActivitiesByUserIdAndDateBetween(id, dateRequestModel.getStartDate(), dateRequestModel.getEndDate());
        ActivityPerDayModel activityPerDayModel = new ActivityPerDayModel();
        List<DailyActivity> dailyActivities = generateEmptyDailyActivities(dateRequestModel);

        userActivities.forEach(activity -> {
            activityPerDayModel.incrementTotalHours(activity.getTime(), activity.getOvertime());
            DailyActivity matchingDailyActivity = dailyActivities.stream()
                    .filter(dailyActivity -> dailyActivity.getDate().equals(activity.getDate()))
                    .findFirst()
                    .orElse(null);
            if(matchingDailyActivity != null){
                int index = dailyActivities.indexOf(matchingDailyActivity);
                matchingDailyActivity.setDailyHours(activity.getTime(), activity.getOvertime());
                dailyActivities.set(index, matchingDailyActivity);
            }
        });

        activityPerDayModel.setHoursPerDay(dailyActivities);
        return activityPerDayModel;
    }

    @Override
    public DailyActivitiesModel getDailyActivities(UUID id, LocalDate date) {
        List<Activity> activities = activityJPARepository.findActivitiesByUserIdAndDate(id, date);
        DailyActivitiesModel dailyActivitiesModel = new DailyActivitiesModel();
        List<ActivityModel> activityModels = activities.stream()
                .map(activity -> {
                    dailyActivitiesModel.incrementTotalHours(activity.getTime(), activity.getOvertime());
                    return modelMapper.map(activity, ActivityModel.class);
                })
                .collect(Collectors.toList());
        dailyActivitiesModel.setActivities(activityModels);
        return dailyActivitiesModel;
    }

    private List<DailyActivity> generateEmptyDailyActivities(DateRequestModel dateRequestModel){
        List<DailyActivity> emptyDailyActivities = new ArrayList<>();
        int numberOfDays = (int) ChronoUnit.DAYS.between(dateRequestModel.getStartDate(), dateRequestModel.getEndDate());
        for(int i = 0; i< numberOfDays; i++){
            DailyActivity dailyActivity = new DailyActivity(dateRequestModel.getStartDate().plusDays(i));
            emptyDailyActivities.add(dailyActivity);
        }

        return emptyDailyActivities;
    }

}
