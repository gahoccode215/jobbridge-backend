package com.jobbridge.job.job.controller;

import com.jobbridge.job.common.response.ApiResponseDTO;
import com.jobbridge.job.common.response.PageResponse;
import com.jobbridge.job.common.response.ResponseFactory;
import com.jobbridge.job.job.dto.request.JobCreateRequest;
import com.jobbridge.job.job.dto.request.JobFilterRequest;
import com.jobbridge.job.job.dto.request.JobUpdateRequest;
import com.jobbridge.job.job.dto.request.RejectJobRequest;
import com.jobbridge.job.job.entity.Job;
import com.jobbridge.job.job.enums.JobStatus;
import com.jobbridge.job.job.enums.JobType;
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
    public ApiResponseDTO<?> createJob(@RequestBody JobCreateRequest request) {

        jobService.createJob(request);

        return ResponseFactory.successMessage("Tạo tin tuyển dụng thành công");
    }

    @PutMapping("/{id}")
    public ApiResponseDTO<?> updateJob(@RequestBody JobUpdateRequest request,
                                       @PathVariable Long id) {

        jobService.updateJob(request, id);

        return ResponseFactory.successMessage("Cập nhật tin tuyển dụng thành công");
    }

    @DeleteMapping("/{id}")
    public ApiResponseDTO<?> deleteJob(@PathVariable Long id) {

        jobService.deleteJob(id);

        return ResponseFactory.successMessage("Xóa tin tuyển dụng thành công");
    }

    @GetMapping
    public ApiResponseDTO<PageResponse<Job>> getJobs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) JobType jobType,
            @RequestParam(required = false) JobStatus status,
            @RequestParam(required = false) String employerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        JobFilterRequest filter = new JobFilterRequest();
        filter.setKeyword(keyword);
        filter.setLocation(location);
        filter.setJobType(jobType);
        filter.setStatus(status);
        filter.setEmployerId(employerId);

        var result = jobService.filterJobs(filter, page, size);

        return ResponseFactory.success(result, "Lấy danh sách job thành công");
    }


    @GetMapping("/search")
    public ApiResponseDTO<List<JobDocument>> searchJobs(
            @RequestParam String keyword) {

        List<JobDocument> result = jobSearchService.search(keyword);

        return ResponseFactory.success(result, "Tìm kiếm tin tuyển dụng thành công");
    }

    @PutMapping("/{id}/submit")
    public ApiResponseDTO<?> submitJob(@PathVariable Long id) {

        jobService.submitJob(id);

        return ResponseFactory.successMessage("Gửi tin tuyển dụng chờ phê duyệt thành công");
    }

    @PutMapping("/{id}/approve")
    public ApiResponseDTO<?> approveJob(@PathVariable Long id,
                                        @RequestParam String adminId) {

        jobService.approveJob(id, adminId);

        return ResponseFactory.successMessage("Phê duyệt tin tuyển dụng thành công");
    }

    @PutMapping("/{id}/reject")
    public ApiResponseDTO<?> rejectJob(@PathVariable Long id,
                                       @RequestBody RejectJobRequest request) {

        jobService.rejectJob(id, request);

        return ResponseFactory.successMessage("Từ chối tin tuyển dụng thành công");
    }
}
