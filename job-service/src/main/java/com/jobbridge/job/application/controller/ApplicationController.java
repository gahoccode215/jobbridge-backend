package com.jobbridge.job.application.controller;

import com.jobbridge.job.application.dto.request.ApplyJobRequest;
import com.jobbridge.job.application.service.ApplicationService;
import com.jobbridge.job.common.response.ApiResponseDTO;
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
}
