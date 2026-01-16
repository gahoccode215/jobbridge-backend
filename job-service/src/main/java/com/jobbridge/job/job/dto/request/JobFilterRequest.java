package com.jobbridge.job.job.dto.request;

import com.jobbridge.job.job.enums.JobStatus;
import com.jobbridge.job.job.enums.JobType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobFilterRequest {

    private String keyword;

    private String location;

    private JobType jobType;

    private JobStatus status;

    private String employerId;
}
