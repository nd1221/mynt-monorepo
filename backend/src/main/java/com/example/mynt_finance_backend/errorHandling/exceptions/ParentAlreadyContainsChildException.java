package com.example.mynt_finance_backend.errorHandling.exceptions;

public class ParentAlreadyContainsChildException extends RuntimeException {
    public ParentAlreadyContainsChildException(String errorMessage) {
        super(errorMessage);
    }
}
