package com.jobbridge.job.application.service.impl;

import com.jobbridge.job.application.dto.request.ApplicationFilterRequest;
import com.jobbridge.job.application.dto.request.ApplyJobRequest;
import com.jobbridge.job.application.dto.request.RejectApplicationRequest;
import com.jobbridge.job.application.dto.response.ApplicationResponse;
import com.jobbridge.job.application.entity.Application;
import com.jobbridge.job.application.enums.ApplicationStatus;
import com.jobbridge.job.application.repository.ApplicationRepository;
import com.jobbridge.job.application.service.ApplicationService;
import com.jobbridge.job.application.specification.ApplicationSpecification;
import com.jobbridge.job.common.response.PageResponse;
import com.jobbridge.job.job.entity.Job;
import com.jobbridge.job.job.enums.JobStatus;
import com.jobbridge.job.job.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository repository;
    private final JobRepository jobRepository;

    @Override
    public void applyJob(ApplyJobRequest request) {

        // 1. Kiểm tra job tồn tại
        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new RuntimeException("Job không tồn tại"));

        // 2. Kiểm tra job đã được duyệt chưa
        if (job.getStatus() != JobStatus.APPROVED) {
            throw new RuntimeException("Job chưa sẵn sàng để ứng tuyển");
        }

        // 3. Kiểm tra đã apply chưa
        boolean existed = repository.existsByJobIdAndCandidateId(
                request.getJobId(),
                request.getCandidateId()
        );

        if (existed) {
            throw new RuntimeException("Bạn đã ứng tuyển job này rồi");
        }

        // 4. Tạo application
        Application application = Application.builder()
                .jobId(request.getJobId())
                .candidateId(request.getCandidateId())
                .employerId(job.getEmployerId())
                .cvUrl(request.getCvUrl())
                .coverLetter(request.getCoverLetter())
                .status(ApplicationStatus.APPLIED)
                .build();

        repository.save(application);
    }

    @Override
    public void withdrawApplication(Long id, String candidateId) {

        Application app = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application không tồn tại"));

        if (!app.getCandidateId().equals(candidateId)) {
            throw new RuntimeException("Không có quyền rút application này");
        }

        if (app.getStatus() != ApplicationStatus.APPLIED &&
                app.getStatus() != ApplicationStatus.REVIEWING) {

            throw new RuntimeException("Không thể rút application ở trạng thái này");
        }

        app.setStatus(ApplicationStatus.WITHDRAWN);

        repository.save(app);
    }

    @Override
    public void markReviewing(Long id, String employerId) {

    }

    @Override
    public void approveApplication(Long id, String employerId) {

        Application app = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application không tồn tại"));

        if (!app.getEmployerId().equals(employerId)) {
            throw new RuntimeException("Không có quyền duyệt application này");
        }

        app.setStatus(ApplicationStatus.APPROVED);

        repository.save(app);
    }


    @Override
    public void rejectApplication(Long id, String employerId, RejectApplicationRequest request) {

        Application app = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application không tồn tại"));

        if (!app.getEmployerId().equals(employerId)) {
            throw new RuntimeException("Không có quyền từ chối application này");
        }

        app.setStatus(ApplicationStatus.REJECTED);
        app.setReviewNote(request.getReason());

        repository.save(app);
    }


    @Override
    public List<ApplicationResponse> getByJob(Long jobId) {

        return repository.findByJobId(jobId)
                .stream()
                .map(this::toResponse)
                .toList();
    }


    @Override
    public List<ApplicationResponse> getByEmployer(String employerId) {
        return List.of();
    }

    @Override
    public List<ApplicationResponse> getByCandidate(String candidateId) {
        return List.of();
    }



    @Override
    public PageResponse<ApplicationResponse> filterApplications(
            ApplicationFilterRequest filter,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);

        Specification<Application> spec =
                ApplicationSpecification.filter(filter);

        Page<Application> applicationPage =
                repository.findAll(spec, pageable);

        List<ApplicationResponse> responses =
                applicationPage.getContent()
                        .stream()
                        .map(this::toResponse)
                        .toList();

        return PageResponse.<ApplicationResponse>builder()
                .content(responses)
                .page(page)
                .size(size)
                .totalElements(applicationPage.getTotalElements())
                .totalPages(applicationPage.getTotalPages())
                .build();
    }

    private ApplicationResponse toResponse(Application app) {
        return ApplicationResponse.builder()
                .id(app.getId())
                .jobId(app.getJobId())
                .candidateId(app.getCandidateId())
                .employerId(app.getEmployerId())
                .cvUrl(app.getCvUrl())
                .coverLetter(app.getCoverLetter())
                .status(app.getStatus())
                .appliedAt(app.getAppliedAt())
                .reviewNote(app.getReviewNote())
                .build();
    }


}

