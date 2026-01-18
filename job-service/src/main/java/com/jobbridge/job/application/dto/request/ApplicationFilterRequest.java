package com.jobbridge.job.application.dto.request;

import com.jobbridge.job.application.enums.ApplicationStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationFilterRequest {

    private Long jobId;

    private String candidateId;

    private String employerId;

    private ApplicationStatus status;
}
