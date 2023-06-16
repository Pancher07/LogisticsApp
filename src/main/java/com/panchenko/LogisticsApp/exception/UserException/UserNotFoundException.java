package com.panchenko.LogisticsApp.exception.UserException;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
