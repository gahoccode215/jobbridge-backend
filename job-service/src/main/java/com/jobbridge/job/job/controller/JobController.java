package com.jobbridge.job.job.controller;

import com.jobbridge.job.job.dto.request.JobCreateRequest;
import com.jobbridge.job.job.dto.request.JobUpdateRequest;
import com.jobbridge.job.job.entity.Job;
import com.jobbridge.job.job.enums.JobStatus;
import com.jobbridge.job.job.service.JobService;
import com.jobbridge.job.search.document.JobDocument;
import com.jobbridge.job.search.service.JobSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
    private final JobSearchService jobSearchService;

    @PostMapping
    public void createJob(@RequestBody JobCreateRequest request) {
        jobService.createJob(request);
    }

    @PutMapping("/{id}")
    public void updateJob(@RequestBody JobUpdateRequest request,
                          @PathVariable Long id) {
        jobService.updateJob(request, id);
    }

    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
    }

    @PutMapping("/{id}/status")
    public void changeStatus(@PathVariable Long id,
                             @RequestParam JobStatus status) {
        jobService.changeStatusJob(status, id);
    }

    @GetMapping
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    @GetMapping("/search")
    public List<JobDocument> searchJobs(
            @RequestParam String keyword) {

        return jobSearchService.search(keyword);
    }
}
