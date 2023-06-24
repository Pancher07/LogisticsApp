package com.panchenko.LogisticsApp.exception;

public class EntityNotUpdatedException extends RuntimeException {
    public EntityNotUpdatedException(String str) {
        super(str);
    }
}
