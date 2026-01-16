package com.jobbridge.job.common.exception;

import com.jobbridge.job.common.response.ApiResponse;
import com.jobbridge.job.common.response.ResponseFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ApiResponse<?> handleRuntimeException(RuntimeException ex) {

        return ResponseFactory.error(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                null
        );
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception ex) {

        return ResponseFactory.error(
                "Internal Server Error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage()
        );
    }
}
