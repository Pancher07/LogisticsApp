package com.panchenko.LogisticsApp.exception.forUser;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
