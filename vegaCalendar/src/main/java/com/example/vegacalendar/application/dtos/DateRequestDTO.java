package com.example.vegacalendar.application.dtos;

import java.beans.ConstructorProperties;
import java.time.LocalDate;

public class DateRequestDTO {
    private LocalDate startDate;
    private LocalDate endDate;

    @ConstructorProperties({"startDate", "endDate"})
    public DateRequestDTO(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public DateRequestDTO() {
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
