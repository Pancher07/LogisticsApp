package com.panchenko.LogisticsApp.exception.ContractorException;

public class ContractorErrorResponse {
    private String message;

    public ContractorErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
