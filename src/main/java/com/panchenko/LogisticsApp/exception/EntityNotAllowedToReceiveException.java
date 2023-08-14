package com.panchenko.LogisticsApp.exception;

public class EntityNotAllowedToReceiveException extends RuntimeException {
    public EntityNotAllowedToReceiveException(String str) {
        super(str);
    }
}
