package com.panchenko.LogisticsApp.exception.DriverException;

public class DriverNotFoundException extends RuntimeException {
    public DriverNotFoundException(String message) {
        super(message);
    }
}
