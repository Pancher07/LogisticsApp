package com.panchenko.LogisticsApp.exception.ProjectException;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String message) {
        super(message);
    }
}
