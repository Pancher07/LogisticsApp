package com.panchenko.LogisticsApp.exception.forUser;

public class UserNotCreatedException extends RuntimeException {
    public UserNotCreatedException(String str) {
        super(str);
    }
}
