package com.panchenko.LogisticsApp.exception.ProjectException;

public class ProjectErrorResponse {
    private String message;

    public ProjectErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
