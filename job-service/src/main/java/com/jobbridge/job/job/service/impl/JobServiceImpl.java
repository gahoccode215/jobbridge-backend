package com.jobbridge.job.job.service.impl;

import com.jobbridge.job.job.dto.request.JobCreateRequest;
import com.jobbridge.job.job.dto.request.JobUpdateRequest;
import com.jobbridge.job.job.entity.Job;
import com.jobbridge.job.job.enums.JobStatus;
import com.jobbridge.job.job.exception.JobNotFoundException;
import com.jobbridge.job.job.repository.JobRepository;
import com.jobbridge.job.job.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

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

        jobRepository.save(job);
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
    }

    @Override
    public void deleteJob(Long jobId) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new JobNotFoundException(jobId));

        jobRepository.delete(job);
    }

    @Override
    public void changeStatusJob(JobStatus jobStatus, Long jobId) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new JobNotFoundException(jobId));

        job.setStatus(jobStatus);

        jobRepository.save(job);
    }

    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
}
