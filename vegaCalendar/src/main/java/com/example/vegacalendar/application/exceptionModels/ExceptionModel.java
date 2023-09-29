package com.example.vegacalendar.application.exceptionModels;

public class ExceptionModel {
    private String errorMessage;

    public ExceptionModel(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
