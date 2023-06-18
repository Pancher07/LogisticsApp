package com.panchenko.LogisticsApp.exception.TrailerException;

public class TrailerNotFoundException extends RuntimeException{
    public TrailerNotFoundException(String message) {
        super(message);
    }
}
