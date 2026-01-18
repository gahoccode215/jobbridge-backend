package com.jobbridge.job.application.controller;

import com.jobbridge.job.application.dto.request.ApplicationFilterRequest;
import com.jobbridge.job.application.dto.request.ApplyJobRequest;
import com.jobbridge.job.application.dto.response.ApplicationResponse;
import com.jobbridge.job.application.enums.ApplicationStatus;
import com.jobbridge.job.application.service.ApplicationService;
import com.jobbridge.job.common.response.ApiResponseDTO;
import com.jobbridge.job.common.response.PageResponse;
import com.jobbridge.job.common.response.ResponseFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService service;

    @PostMapping
    public ApiResponseDTO<?> apply(@RequestBody ApplyJobRequest request) {

        service.applyJob(request);

        return ResponseFactory.successMessage("Nộp đơn ứng tuyển thành công");
    }

    @PutMapping("/{id}/withdraw")
    public ApiResponseDTO<?> withdraw(
            @PathVariable Long id,
            @RequestParam String candidateId) {

        service.withdrawApplication(id, candidateId);

        return ResponseFactory.successMessage("Rút đơn thành công");
    }

    @GetMapping("/job/{jobId}")
    public ApiResponseDTO<?> getByJob(@PathVariable Long jobId) {

        return ResponseFactory.success(
                service.getByJob(jobId),
                "Lấy danh sách ứng viên thành công"
        );
    }

    @GetMapping
    public ApiResponseDTO<PageResponse<ApplicationResponse>> filterApplications(
            @RequestParam(required = false) Long jobId,
            @RequestParam(required = false) String candidateId,
            @RequestParam(required = false) String employerId,
            @RequestParam(required = false) ApplicationStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        ApplicationFilterRequest filter = new ApplicationFilterRequest();

        filter.setJobId(jobId);
        filter.setCandidateId(candidateId);
        filter.setEmployerId(employerId);
        filter.setStatus(status);

        var result = service.filterApplications(filter, page, size);

        return ResponseFactory.success(
                result,
                "Lấy danh sách application thành công"
        );
    }
}
