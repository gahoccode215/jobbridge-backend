package com.jobbridge.job.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pagination {

    private int page;

    private int size;

    private long totalElements;

    private int totalPages;
}

