package com.aplication.homeFinder.errorHandler;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends Exception {



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorRespond> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return new ResponseEntity<>(new ErrorRespond(HttpStatus.BAD_REQUEST, errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorRespond> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(new ErrorRespond(HttpStatus.BAD_REQUEST, "Invalid JSON format"), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorRespond> handleHttpMessageNotExist(ResponseStatusException ex) {
        return new ResponseEntity<>(new ErrorRespond(ex.getStatusCode(), ex.getMessage()), ex.getStatusCode());
    }


}
