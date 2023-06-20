package com.panchenko.LogisticsApp.exception.ContractorException;

public class ContractorNotFoundException extends RuntimeException {
    public ContractorNotFoundException(String message) {
        super(message);
    }
}
