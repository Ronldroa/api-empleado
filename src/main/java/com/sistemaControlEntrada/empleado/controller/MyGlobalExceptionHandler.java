package com.sistemaControlEntrada.empleado.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.sistemaControlEntrada.empleado.exception.EntityYaRegistradaException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class MyGlobalExceptionHandler {

    @ExceptionHandler(EntityYaRegistradaException.class)
    public final ResponseEntity<Object> handleYaRegistradaException(EntityYaRegistradaException ex,
                                                                    WebRequest request) {
        return new ResponseEntity(new Error(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectResultSizeDataAccessException.class)
    public final ResponseEntity<Object> handleYaRegistradaException(IncorrectResultSizeDataAccessException ex,
                                                                    WebRequest request) {
        return new ResponseEntity(new Error(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(EntityNotFoundException ex,
                                                                    WebRequest request) {
        return new ResponseEntity(new Error(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,
                                                                           WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", ex.getConstraintViolations().stream().map(error -> error.getMessage()).collect(Collectors.toList()));

        return new ResponseEntity(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(DataIntegrityViolationException ex,
                                                                    WebRequest request) {
        return new ResponseEntity(new Error(ex.getRootCause().getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public final ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
                                                                     WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", ex.getValue() + " no es un valor válido para el campo " + ex.getPath().get(0).getFieldName());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    //@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status", status.value());

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);

    }

    @AllArgsConstructor
    @Getter
    class Error {

        private String message;
    }

}