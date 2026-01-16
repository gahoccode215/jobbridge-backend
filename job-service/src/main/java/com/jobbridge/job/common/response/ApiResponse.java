package com.jobbridge.job.common.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {

    private Meta meta;

    private T data;

    private Object errors;
}
