package com.panchenko.LogisticsApp.exception;

import com.panchenko.LogisticsApp.exception.DriverException.DriverErrorResponse;
import com.panchenko.LogisticsApp.exception.DriverException.DriverNotCreatedException;
import com.panchenko.LogisticsApp.exception.DriverException.DriverNotFoundException;
import com.panchenko.LogisticsApp.exception.DriverException.DriverNotUpdatedException;
import com.panchenko.LogisticsApp.exception.TrailerException.TrailerErrorResponse;
import com.panchenko.LogisticsApp.exception.TrailerException.TrailerNotCreatedException;
import com.panchenko.LogisticsApp.exception.TrailerException.TrailerNotFoundException;
import com.panchenko.LogisticsApp.exception.TrailerException.TrailerNotUpdatedException;
import com.panchenko.LogisticsApp.exception.TruckTractorException.TruckTractorErrorResponse;
import com.panchenko.LogisticsApp.exception.TruckTractorException.TruckTractorNotCreatedException;
import com.panchenko.LogisticsApp.exception.TruckTractorException.TruckTractorNotFoundException;
import com.panchenko.LogisticsApp.exception.TruckTractorException.TruckTractorNotUpdatedException;
import com.panchenko.LogisticsApp.exception.UserException.UserErrorResponse;
import com.panchenko.LogisticsApp.exception.UserException.UserNotCreatedException;
import com.panchenko.LogisticsApp.exception.UserException.UserNotFoundException;
import com.panchenko.LogisticsApp.exception.UserException.UserNotUpdatedException;
import com.panchenko.LogisticsApp.model.TruckTractor;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(ex.getBindingResult().toString());
    }

    @ExceptionHandler
    public ResponseEntity<?> handleNullEntityReferenceException(NullEntityReferenceException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        UserErrorResponse response = new UserErrorResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleUserNotCreatedException(UserNotCreatedException ex) {
        UserErrorResponse response = new UserErrorResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleUserNotUpdatedException(UserNotUpdatedException ex) {
        UserErrorResponse response = new UserErrorResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<DriverErrorResponse> handleDriverNotFoundException(DriverNotFoundException ex) {
        DriverErrorResponse response = new DriverErrorResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<DriverErrorResponse> handleDriverNotCreatedException(DriverNotCreatedException ex) {
        DriverErrorResponse response = new DriverErrorResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<DriverErrorResponse> handleDriverNotUpdatedException(DriverNotUpdatedException ex) {
        DriverErrorResponse response = new DriverErrorResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<TruckTractorErrorResponse> handleTruckTractorNotFoundException(TruckTractorNotFoundException ex) {
        TruckTractorErrorResponse response = new TruckTractorErrorResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<TruckTractorErrorResponse> handleTruckTractorNotCreatedException(TruckTractorNotCreatedException ex) {
        TruckTractorErrorResponse response = new TruckTractorErrorResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<TruckTractorErrorResponse> handleTruckTractorNotUpdatedException(TruckTractorNotUpdatedException ex) {
        TruckTractorErrorResponse response = new TruckTractorErrorResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<TrailerErrorResponse> handleTrailerNotFoundException(TrailerNotFoundException ex) {
        TrailerErrorResponse response = new TrailerErrorResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<TrailerErrorResponse> handleTrailerNotCreatedException(TrailerNotCreatedException ex) {
        TrailerErrorResponse response = new TrailerErrorResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<TrailerErrorResponse> handleTrailerNotUpdatedException(TrailerNotUpdatedException ex) {
        TrailerErrorResponse response = new TrailerErrorResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

