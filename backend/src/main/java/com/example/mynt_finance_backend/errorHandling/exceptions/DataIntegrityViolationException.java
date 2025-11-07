package com.example.mynt_finance_backend.errorHandling.exceptions;

public class DataIntegrityViolationException extends RuntimeException {
    public DataIntegrityViolationException(String errorMessage) {
        super(errorMessage);
    }
}
