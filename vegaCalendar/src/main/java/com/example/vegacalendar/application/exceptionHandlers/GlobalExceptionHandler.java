package com.example.vegacalendar.application.exceptionHandlers;

import com.example.vegacalendar.application.exceptionModels.ExceptionModel;
import com.example.vegacalendar.core.exceptions.JwtExpiredException;
import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.exceptions.UserWithSameEmailException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception){
        return new ResponseEntity<>(new ExceptionModel(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserWithSameEmailException.class)
    public ResponseEntity<?> handleUserAlreadtExistException(UserWithSameEmailException exception){
        return new ResponseEntity<>(new ExceptionModel(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(JwtExpiredException.class)
    public ResponseEntity<?> handleExpiredJwtException(JwtExpiredException exception){
        return new ResponseEntity<>(new ExceptionModel(exception.getMessage()), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> handleSQLIntegritiyyContraintViolationException(SQLIntegrityConstraintViolationException exception){
        return new ResponseEntity<>(new ExceptionModel(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException exception){
        return new ResponseEntity<>(new ExceptionModel(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
