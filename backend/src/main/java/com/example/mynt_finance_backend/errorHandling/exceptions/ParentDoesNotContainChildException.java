package com.example.mynt_finance_backend.errorHandling.exceptions;

public class ParentDoesNotContainChildException extends RuntimeException {
    public ParentDoesNotContainChildException(String errorMessage) {
        super(errorMessage);
    }
}
