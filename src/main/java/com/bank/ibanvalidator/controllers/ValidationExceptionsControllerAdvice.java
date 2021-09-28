package com.bank.ibanvalidator.controllers;

import com.bank.ibanvalidator.controllers.responses.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = {RestController.class})
class ValidationExceptionsControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleBindingErrors(
            MethodArgumentNotValidException ex) {
        String fieldName = ex.getFieldErrors().get(0).getField();
        String errorMessage = ex.getFieldErrors().get(0).getDefaultMessage();

        ValidationErrorResponse validationError =
                new ValidationErrorResponse(HttpStatus.BAD_REQUEST.value(), fieldName, errorMessage);

        return new ResponseEntity<>(validationError, HttpStatus.BAD_REQUEST);
    }
}
