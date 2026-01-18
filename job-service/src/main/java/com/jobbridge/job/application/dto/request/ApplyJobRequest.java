package com.jobbridge.job.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplyJobRequest {
    private Long jobId;
    private String candidateId;
    private String cvUrl;
    private String coverLetter;
}

