package com.panchenko.LogisticsApp.exception.DriverException;

public class DriverErrorResponse {
    private String message;

    public DriverErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}