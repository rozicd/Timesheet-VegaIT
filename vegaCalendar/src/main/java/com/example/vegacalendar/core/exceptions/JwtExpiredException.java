package com.example.vegacalendar.core.exceptions;

public class JwtExpiredException extends Exception{
    public JwtExpiredException(String message) {
        super(message);
    }
}
