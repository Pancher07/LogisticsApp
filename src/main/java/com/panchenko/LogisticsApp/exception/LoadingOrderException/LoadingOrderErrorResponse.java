package com.panchenko.LogisticsApp.exception.LoadingOrderException;

public class LoadingOrderErrorResponse {
    private String message;

    public LoadingOrderErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
