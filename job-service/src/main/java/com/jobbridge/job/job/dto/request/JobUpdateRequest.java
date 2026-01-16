package com.jobbridge.job.job.dto.request;

import com.jobbridge.job.job.enums.JobType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class JobUpdateRequest {

    private String title;

    private String description;

    private String requirements;

    private String location;

    private Double salary;

    private JobType jobType;

    private LocalDateTime expiredAt;
}
