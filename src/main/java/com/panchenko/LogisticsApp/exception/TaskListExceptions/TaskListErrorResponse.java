package com.panchenko.LogisticsApp.exception.TaskListExceptions;

public class TaskListErrorResponse {
    private String message;

    public TaskListErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
