package com.panchenko.LogisticsApp.exception.TaskListExceptions;

public class TaskListNotCreatedException extends RuntimeException {
    public TaskListNotCreatedException(String str) {
        super(str);
    }
}
