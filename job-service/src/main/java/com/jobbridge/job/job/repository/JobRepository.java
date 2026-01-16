package com.jobbridge.job.job.repository;

import com.jobbridge.job.job.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
