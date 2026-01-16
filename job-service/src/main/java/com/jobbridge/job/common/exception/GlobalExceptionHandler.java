package com.jobbridge.job.common.exception;

import com.jobbridge.job.common.response.ApiResponseDTO;
import com.jobbridge.job.common.response.ResponseFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponseDTO<?> handleRuntimeException(RuntimeException ex) {

        return ResponseFactory.error(
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponseDTO<?> handleException(Exception ex) {

        return ResponseFactory.error(
                "Internal Server Error",
                ex.getMessage()
        );
    }
}
