package com.jobbridge.job.search.dto;

import lombok.Data;

@Data
public class JobSearchRequest {

    private String keyword;

    private String location;

    private String jobType;

    private Double salaryFrom;

    private Double salaryTo;

    private int page = 0;

    private int size = 10;
}
