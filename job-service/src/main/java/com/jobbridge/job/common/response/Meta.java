package com.jobbridge.job.common.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Meta {

    private int status;

    private String message;

    private LocalDateTime timestamp;
}
