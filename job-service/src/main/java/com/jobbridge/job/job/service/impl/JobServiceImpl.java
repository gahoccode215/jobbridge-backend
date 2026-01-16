package com.jobbridge.job.job.service.impl;

import com.jobbridge.job.job.dto.request.JobCreateRequest;
import com.jobbridge.job.job.dto.request.JobUpdateRequest;
import com.jobbridge.job.job.dto.request.RejectJobRequest;
import com.jobbridge.job.job.entity.Job;
import com.jobbridge.job.job.enums.JobStatus;
import com.jobbridge.job.job.exception.JobNotFoundException;
import com.jobbridge.job.job.repository.JobRepository;
import com.jobbridge.job.job.service.JobService;
import com.jobbridge.job.search.service.JobIndexService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final JobIndexService jobIndexService;

    @Override
    public void createJob(JobCreateRequest request) {

        Job job = Job.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .requirements(request.getRequirements())
                .location(request.getLocation())
                .salary(request.getSalary())
                .jobType(request.getJobType())
                .employerId(request.getEmployerId())
                .expiredAt(request.getExpiredAt())
                .build();

        Job savedJob = jobRepository.save(job);

        jobIndexService.indexJob(savedJob);
    }

    @Override
    public void updateJob(JobUpdateRequest request, Long jobId) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new JobNotFoundException(jobId));

        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setRequirements(request.getRequirements());
        job.setLocation(request.getLocation());
        job.setSalary(request.getSalary());
        job.setJobType(request.getJobType());
        job.setExpiredAt(request.getExpiredAt());

        jobRepository.save(job);

        jobIndexService.indexJob(job);
    }

    @Override
    public void deleteJob(Long jobId) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new JobNotFoundException(jobId));

        jobRepository.delete(job);
    }


    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    @Override
    public void submitJob(Long jobId) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new JobNotFoundException(jobId));

        if (job.getStatus() != JobStatus.DRAFT) {
            throw new RuntimeException("Only DRAFT job can be submitted");
        }

        job.setStatus(JobStatus.PENDING);

        jobRepository.save(job);
    }

    @Override
    public void approveJob(Long jobId, String adminId) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new JobNotFoundException(jobId));

        if (job.getStatus() != JobStatus.PENDING) {
            throw new RuntimeException("Only PENDING job can be approved");
        }

        job.setStatus(JobStatus.APPROVED);
        job.setApprovedBy(adminId);
        job.setApprovedAt(LocalDateTime.now());

        jobRepository.save(job);

        // index lại để search thấy job mới approved
        jobIndexService.indexJob(job);
    }

    @Override
    public void rejectJob(Long jobId, RejectJobRequest request) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new JobNotFoundException(jobId));

        if (job.getStatus() != JobStatus.PENDING) {
            throw new RuntimeException("Only PENDING job can be rejected");
        }

        job.setStatus(JobStatus.REJECTED);
        job.setRejectReason(request.getReason());
        job.setApprovedBy(request.getAdminId());
        job.setApprovedAt(LocalDateTime.now());

        jobRepository.save(job);
    }


}
