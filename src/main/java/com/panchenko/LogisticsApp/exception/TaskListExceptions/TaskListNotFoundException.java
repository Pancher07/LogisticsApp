package com.panchenko.LogisticsApp.exception.TaskListExceptions;

public class TaskListNotFoundException extends RuntimeException {
    public TaskListNotFoundException(String message) {
        super(message);
    }
}
