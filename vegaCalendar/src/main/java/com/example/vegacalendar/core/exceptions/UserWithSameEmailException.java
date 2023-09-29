package com.example.vegacalendar.core.exceptions;

public class UserWithSameEmailException extends Exception{
    public UserWithSameEmailException(String message) {
        super(message);
    }
}
