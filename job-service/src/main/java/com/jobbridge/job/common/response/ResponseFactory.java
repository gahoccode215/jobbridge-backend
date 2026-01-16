package com.jobbridge.job.common.response;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ResponseFactory {

    public static <T> ApiResponseDTO<T> success(T data, String message) {
        return ApiResponseDTO.<T>builder()
                .meta(new Meta(200, message, LocalDateTime.now()))
                .data(data)
                .errors(null)
                .build();
    }

    public static ApiResponseDTO<?> successMessage(String message) {
        return ApiResponseDTO.builder()
                .meta(new Meta(200, message, LocalDateTime.now()))
                .data(null)
                .errors(null)
                .build();
    }

    public static ApiResponseDTO<?> error(String message, Object errors) {
        return ApiResponseDTO.builder()
                .meta(new Meta(400, message, LocalDateTime.now()))
                .data(null)
                .errors(errors)
                .build();
    }
}
