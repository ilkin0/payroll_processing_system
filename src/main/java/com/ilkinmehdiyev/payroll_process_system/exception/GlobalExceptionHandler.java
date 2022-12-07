package com.ilkinmehdiyev.payroll_process_system.exception;

import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(FileNotSupportedException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(FileNotSupportedException exception) {
        return getErrorResponse(HttpStatus.NOT_ACCEPTABLE, exception.getMessage());
    }

    @ExceptionHandler({IllegalStateException.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(RuntimeException exception) {
        return getErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler({FileParseException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(FileParseException exception) {
        return getErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    private ResponseEntity<ErrorResponse> getErrorResponse(HttpStatus httpStatus, String errorMessage) {
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(),
                httpStatus.getReasonPhrase(), errorMessage, LocalDateTime.now());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}