package com.panchenko.LogisticsApp.exception;

public class EntityNotCreatedException extends RuntimeException {
    public EntityNotCreatedException(String str) {
        super(str);
    }
}
