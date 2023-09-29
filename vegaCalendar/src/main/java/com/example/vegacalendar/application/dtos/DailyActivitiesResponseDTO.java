package com.example.vegacalendar.application.dtos;

import com.example.vegacalendar.core.model.ActivityModel;

import java.util.List;

public class DailyActivitiesResponseDTO {
    List<ActivityResponseDTO> activities;
    double totalHours;

    public DailyActivitiesResponseDTO(List<ActivityResponseDTO> activities, double totalHours) {
        this.activities = activities;
        this.totalHours = totalHours;
    }

    public DailyActivitiesResponseDTO() {
    }

    public List<ActivityResponseDTO> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivityResponseDTO> activities) {
        this.activities = activities;
    }

    public double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(double totalHours) {
        this.totalHours = totalHours;
    }
}
