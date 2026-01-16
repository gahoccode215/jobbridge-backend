package com.jobbridge.job.job.service;

import com.jobbridge.job.common.response.PageResponse;
import com.jobbridge.job.job.dto.request.JobCreateRequest;
import com.jobbridge.job.job.dto.request.JobFilterRequest;
import com.jobbridge.job.job.dto.request.JobUpdateRequest;
import com.jobbridge.job.job.dto.request.RejectJobRequest;
import com.jobbridge.job.job.entity.Job;
import com.jobbridge.job.job.enums.JobStatus;

import java.util.List;

public interface JobService {
    void createJob(JobCreateRequest request);
    void updateJob(JobUpdateRequest request, Long jobId);
    void deleteJob(Long jobId);

    List<Job> getAllJobs();

    void submitJob(Long jobId);

    void approveJob(Long jobId, String adminId);

    void rejectJob(Long jobId, RejectJobRequest request);
    PageResponse<Job> filterJobs(
            JobFilterRequest filter,
            int page,
            int size
    );
}
