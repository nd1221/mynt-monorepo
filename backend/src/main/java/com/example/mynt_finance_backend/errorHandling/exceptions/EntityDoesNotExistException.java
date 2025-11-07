package com.example.mynt_finance_backend.errorHandling.exceptions;

public class EntityDoesNotExistException extends RuntimeException {
    public EntityDoesNotExistException(String errorMessage) {
        super(errorMessage);
    }
}
