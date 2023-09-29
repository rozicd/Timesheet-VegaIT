package com.example.vegacalendar.core.model;

import java.util.List;

public class DailyActivitiesModel {
    List<ActivityModel> activities;
    double totalHours;

    public DailyActivitiesModel(List<ActivityModel> activities) {
        this.activities = activities;
        this.totalHours = 0;
    }

    public DailyActivitiesModel() {
        this.totalHours = 0;
    }

    public List<ActivityModel> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivityModel> activities) {
        this.activities = activities;
    }

    public double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(double totalHours) {
        this.totalHours = totalHours;
    }
    public void incrementTotalHours(double time, double overTime){
        this.totalHours += time;
        this.totalHours += overTime;
    }
}
