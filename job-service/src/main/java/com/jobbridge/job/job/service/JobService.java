package com.jobbridge.job.job.service;

import com.jobbridge.job.job.dto.request.JobCreateRequest;
import com.jobbridge.job.job.dto.request.JobUpdateRequest;
import com.jobbridge.job.job.entity.Job;
import com.jobbridge.job.job.enums.JobStatus;

import java.util.List;

public interface JobService {
    void createJob(JobCreateRequest request);
    void updateJob(JobUpdateRequest request, Long jobId);
    void deleteJob(Long jobId);
    void changeStatusJob(JobStatus jobStatus, Long jobId);
    List<Job> getAllJobs();
}
