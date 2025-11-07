package com.example.mynt_finance_backend.errorHandling;

import com.example.mynt_finance_backend.errorHandling.exceptions.EntityDoesNotExistException;
import com.example.mynt_finance_backend.errorHandling.exceptions.ParentAlreadyContainsChildException;
import com.example.mynt_finance_backend.errorHandling.exceptions.ParentDoesNotContainChildException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = EntityDoesNotExistException.class)
    public ResponseEntity<ErrorResponse> handleEntityDoesNotExist(EntityDoesNotExistException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ParentDoesNotContainChildException.class)
    public ResponseEntity<ErrorResponse> handleParentDoesNotContainChild(ParentDoesNotContainChildException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ParentAlreadyContainsChildException.class)
    public ResponseEntity<ErrorResponse> handleParentAlreadyContainsChild(ParentAlreadyContainsChildException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.OK, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }
}
