package com.example.vegacalendar.core.model;

import java.util.List;

public class ReportModel {
    private List<ActivityModel> activityModels;
    private double totalHours;

    public ReportModel() {
    }

    public ReportModel(List<ActivityModel> activityModels, double totalHours) {
        this.activityModels = activityModels;
        this.totalHours = totalHours;
    }

    public ReportModel(List<ActivityModel> activityModels) {
        this.activityModels = activityModels;
        this.totalHours = 0;
    }

    public List<ActivityModel> getActivityModels() {
        return activityModels;
    }

    public void setActivityModels(List<ActivityModel> activityModels) {
        this.activityModels = activityModels;
    }

    public double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(double totalHours) {
        this.totalHours = totalHours;
    }
    public void increaseHours(double time, double overtime){
        this.totalHours += time;
        this.totalHours += overtime;
    }
}
