package com.jobbridge.job.job.dto.response;

import com.jobbridge.job.job.enums.JobStatus;
import com.jobbridge.job.job.enums.JobType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class JobResponse {

    private Long id;

    private String title;

    private String description;

    private String requirements;

    private String location;

    private Double salary;

    private JobType jobType;

    private JobStatus status;

    private String employerId;

    private LocalDateTime expiredAt;
}
