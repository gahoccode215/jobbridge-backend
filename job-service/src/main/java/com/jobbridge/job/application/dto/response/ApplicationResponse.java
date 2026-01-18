package com.jobbridge.job.application.dto.response;

import com.jobbridge.job.application.enums.ApplicationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ApplicationResponse {

    private Long id;

    private Long jobId;

    private String candidateId;

    private String employerId;

    private String cvUrl;

    private String coverLetter;

    private ApplicationStatus status;

    private LocalDateTime appliedAt;

    private String reviewNote;
}

