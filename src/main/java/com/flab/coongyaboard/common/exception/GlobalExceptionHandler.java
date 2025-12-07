package com.flab.coongyaboard.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {

        FieldError fieldError = ex.getBindingResult()
                .getFieldErrors()
                .get(0);

        ValidationErrorCode vCode =
                ValidationErrorCode.from(fieldError.getCode());

        ErrorResponse body = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .statusDescription(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .field(fieldError.getField())
                .code(vCode.getErrorCode())
                .message(fieldError.getDefaultMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
