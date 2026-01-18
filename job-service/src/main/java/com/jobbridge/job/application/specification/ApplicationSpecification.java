package com.jobbridge.job.application.specification;

import com.jobbridge.job.application.dto.request.ApplicationFilterRequest;
import com.jobbridge.job.application.entity.Application;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ApplicationSpecification {

    public static Specification<Application> filter(ApplicationFilterRequest filter) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (filter.getJobId() != null) {
                predicates.add(
                        cb.equal(root.get("jobId"), filter.getJobId())
                );
            }

            if (filter.getCandidateId() != null) {
                predicates.add(
                        cb.equal(root.get("candidateId"), filter.getCandidateId())
                );
            }

            if (filter.getEmployerId() != null) {
                predicates.add(
                        cb.equal(root.get("employerId"), filter.getEmployerId())
                );
            }

            if (filter.getStatus() != null) {
                predicates.add(
                        cb.equal(root.get("status"), filter.getStatus())
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
