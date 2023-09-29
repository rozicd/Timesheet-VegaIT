package com.example.vegacalendar.core.model;

import java.time.LocalDate;

public class DailyActivity {
    private LocalDate date;
    private double dailyHours;

    public DailyActivity(LocalDate date) {
        this.date = date;
        this.dailyHours = 0;
    }

    public DailyActivity() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getDailyHours() {
        return dailyHours;
    }

    public void setDailyHours(double time, double overtime) {
        this.dailyHours += time;
        this.dailyHours += overtime;
    }
}
