package com.jobbridge.job.job.specification;

import com.jobbridge.job.job.entity.Job;
import com.jobbridge.job.job.enums.JobStatus;
import com.jobbridge.job.job.enums.JobType;
import org.springframework.data.jpa.domain.Specification;

public class JobSpecification {

    public static Specification<Job> hasKeyword(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) return null;

            String like = "%" + keyword.toLowerCase() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("title")), like),
                    cb.like(cb.lower(root.get("description")), like),
                    cb.like(cb.lower(root.get("requirements")), like)
            );
        };
    }

    public static Specification<Job> hasLocation(String location) {
        return (root, query, cb) ->
                location == null ? null :
                        cb.like(cb.lower(root.get("location")),
                                "%" + location.toLowerCase() + "%");
    }

    public static Specification<Job> hasJobType(JobType jobType) {
        return (root, query, cb) ->
                jobType == null ? null :
                        cb.equal(root.get("jobType"), jobType);
    }

    public static Specification<Job> hasStatus(JobStatus status) {
        return (root, query, cb) ->
                status == null ? null :
                        cb.equal(root.get("status"), status);
    }

    public static Specification<Job> hasEmployer(String employerId) {
        return (root, query, cb) ->
                employerId == null ? null :
                        cb.equal(root.get("employerId"), employerId);
    }
}
