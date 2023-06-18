package com.panchenko.LogisticsApp.exception.TrailerException;

public class TrailerErrorResponse {
    private String message;

    public TrailerErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
