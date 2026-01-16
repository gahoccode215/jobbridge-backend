package com.jobbridge.job.job.exception;

public class JobNotFoundException extends RuntimeException {

    public JobNotFoundException(Long id) {
        super("Job not found with id: " + id);
    }
}
