package com.jobbridge.job.search.service;

import com.jobbridge.job.job.entity.Job;
import com.jobbridge.job.search.document.JobDocument;
import com.jobbridge.job.search.repository.JobSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobIndexService {

    private final JobSearchRepository repository;

    public void indexJob(Job job) {

        JobDocument doc = JobDocument.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .requirements(job.getRequirements())
                .location(job.getLocation())
                .salary(job.getSalary())
                .jobType(job.getJobType())
                .employerId(job.getEmployerId())
                .expiredAt(job.getExpiredAt().toLocalDate())
                .build();

        repository.save(doc);
    }
}
