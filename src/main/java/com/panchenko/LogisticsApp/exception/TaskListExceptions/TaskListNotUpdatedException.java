package com.panchenko.LogisticsApp.exception.TaskListExceptions;

public class TaskListNotUpdatedException extends RuntimeException {
    public TaskListNotUpdatedException(String str) {
        super(str);
    }
}
