package com.panchenko.LogisticsApp.exception.ManagerOrderException;

public class ManagerOrderErrorResponse {
    private String message;

    public ManagerOrderErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
