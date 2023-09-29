package com.example.vegacalendar.application.dtos;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ReportResponseDTO {
    private List<ActivityResponseDTO> activityResponseDTOS;
    private double totalHours;

    public ReportResponseDTO() {
    }

    public ReportResponseDTO(List<ActivityResponseDTO> activityResponseDTOS, double totalHours) {
        this.activityResponseDTOS = activityResponseDTOS;
        this.totalHours = totalHours;
    }

    public List<ActivityResponseDTO> getActivityResponseDTOS() {
        return activityResponseDTOS;
    }

    public void setActivityResponseDTOS(List<ActivityResponseDTO> activityResponseDTOS) {
        this.activityResponseDTOS = activityResponseDTOS;
    }

    public double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(double totalHours) {
        this.totalHours = totalHours;
    }
}
