package com.panchenko.LogisticsApp.util;

import com.panchenko.LogisticsApp.exception.EntityNotCreatedException;
import com.panchenko.LogisticsApp.exception.EntityNotUpdatedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public abstract class CheckErrors {
    public static void checkErrorsForCreate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new EntityNotCreatedException(errorMessage.toString());
        }
    }

    public static void checkErrorsForUpdate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new EntityNotUpdatedException(errorMessage.toString());
        }
    }
}
