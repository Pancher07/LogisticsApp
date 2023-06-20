package com.panchenko.LogisticsApp.exception.HitchException;

public class HitchErrorResponse {
    private String message;

    public HitchErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
