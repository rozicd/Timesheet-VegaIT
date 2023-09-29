package com.example.vegacalendar.core.model;

import java.util.ArrayList;
import java.util.List;

public class ActivityPerDayModel {
    private List<DailyActivity> hoursPerDay;
    private Double totalHours;

    public ActivityPerDayModel(List<DailyActivity> hoursPerDay, Double totalHours) {
        this.hoursPerDay = hoursPerDay;
        this.totalHours = totalHours;
    }

    public ActivityPerDayModel() {
        this.hoursPerDay = new ArrayList<>();
        this.totalHours = (double) 0;
    }


    public List<DailyActivity> getHoursPerDay() {
        return hoursPerDay;
    }

    public void setHoursPerDay(List<DailyActivity> hoursPerDay) {
        this.hoursPerDay = hoursPerDay;
    }

    public Double getTotalHours() {
        return totalHours;
    }

    public void incrementTotalHours(Double time, Double overTime) {
        this.totalHours += time;
        this.totalHours += overTime;
    }
}
