package com.panchenko.LogisticsApp.exception.TruckTractorException;

public class TruckTractorErrorResponse {
    private String message;

    public TruckTractorErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
