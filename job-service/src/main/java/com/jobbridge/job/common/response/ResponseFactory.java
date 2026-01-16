package com.jobbridge.job.common.response;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ResponseFactory {

    public static <T> ApiResponse<T> success(T data) {
        return buildResponse(HttpStatus.OK.value(), "Success", data, null);
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return buildResponse(HttpStatus.OK.value(), message, data, null);
    }

    public static ApiResponse<?> successMessage(String message) {
        return buildResponse(HttpStatus.OK.value(), message, null, null);
    }

    public static ApiResponse<?> error(String message, int status, Object errors) {
        return buildResponse(status, message, null, errors);
    }

    private static <T> ApiResponse<T> buildResponse(
            int status,
            String message,
            T data,
            Object errors
    ) {
        return ApiResponse.<T>builder()
                .meta(Meta.builder()
                        .status(status)
                        .message(message)
                        .timestamp(LocalDateTime.now())
                        .build())
                .data(data)
                .errors(errors)
                .build();
    }
}
