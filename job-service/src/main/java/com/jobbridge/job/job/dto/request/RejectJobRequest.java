package com.jobbridge.job.job.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RejectJobRequest {
    private String reason;
    private String adminId;
}
