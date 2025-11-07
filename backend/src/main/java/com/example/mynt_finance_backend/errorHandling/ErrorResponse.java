package com.example.mynt_finance_backend.errorHandling;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorResponse {

    private HttpStatus errorCode;

    private final String errorMessage;

    private final LocalDateTime timeStamp;

    public ErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
        this.timeStamp = LocalDateTime.now();
    }

    public ErrorResponse(HttpStatus errorCode, String errorMessage) {
        this(errorMessage);
        this.errorCode = errorCode;
    }

    public HttpStatus getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public LocalDateTime getTimeStamp() {
        return this.timeStamp;
    }
}