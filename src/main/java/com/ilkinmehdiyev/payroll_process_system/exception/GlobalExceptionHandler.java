package com.ilkinmehdiyev.payroll_process_system.exception;

import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

//    @ExceptionHandler(FileNotSupportedException.class)


    private ErrorResponse getErrorResponse(HttpStatus httpStatus, String errorMessage) {
        return new ErrorResponse(httpStatus.value(), httpStatus.getReasonPhrase(), errorMessage, LocalDateTime.now());
    }
}
