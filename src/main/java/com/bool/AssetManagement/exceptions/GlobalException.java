package com.bool.AssetManagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException  {
    @ExceptionHandler
    public ResponseEntity<?> handleVehicleAlreadyExistsException(VehicleAlreadyExistsException exception){
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleVehicleNotFoundException(VehicleNotFoundException exception){
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(Exception exception){
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.CONFLICT);
    }
}
