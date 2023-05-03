package com.example.eshop.service.exceptions;

import com.example.exceptions.DomainValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MainExceptionHandler {

    @ExceptionHandler(DomainValidationException.class)
    public ResponseEntity<?> handleDomainViolationsException(DomainValidationException exception) {
        return ResponseEntity.badRequest().body(exception.getValidationErrors());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

}
