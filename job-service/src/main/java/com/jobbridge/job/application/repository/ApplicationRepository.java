package com.jobbridge.job.application.repository;

import com.jobbridge.job.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByJobIdAndCandidateId(Long jobId, String candidateId);

    List<Application> findByJobId(Long jobId);

    List<Application> findByEmployerId(String employerId);

    List<Application> findByCandidateId(String candidateId);
}
