package com.jobbridge.job.application.service;

import com.jobbridge.job.application.dto.request.ApplyJobRequest;
import com.jobbridge.job.application.dto.request.RejectApplicationRequest;
import com.jobbridge.job.application.dto.response.ApplicationResponse;

import java.util.List;

public interface ApplicationService {

    void applyJob(ApplyJobRequest request);

    void withdrawApplication(Long id, String candidateId);

    void markReviewing(Long id, String employerId);

    void approveApplication(Long id, String employerId);

    void rejectApplication(Long id, String employerId, RejectApplicationRequest request);

    List<ApplicationResponse> getByJob(Long jobId);

    List<ApplicationResponse> getByEmployer(String employerId);

    List<ApplicationResponse> getByCandidate(String candidateId);
}

