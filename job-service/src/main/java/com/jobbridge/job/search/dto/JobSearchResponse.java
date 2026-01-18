package com.jobbridge.job.search.dto;

import com.jobbridge.job.search.document.JobDocument;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JobSearchResponse {

    private List<JobDocument> results;

    private Pagination pagination;

    private long took;
}

