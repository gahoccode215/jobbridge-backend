package com.jobbridge.job.search.service;

import com.jobbridge.job.search.document.JobDocument;
import com.jobbridge.job.search.dto.JobSearchRequest;
import com.jobbridge.job.search.dto.JobSearchResponse;
import com.jobbridge.job.search.dto.Pagination;
import com.jobbridge.job.search.repository.JobSearchRepository;
import com.jobbridge.job.search.util.JobNativeQueryBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobSearchService {

    private final ElasticsearchOperations operations;

    public JobSearchResponse search(JobSearchRequest request) {

        var query = JobNativeQueryBuilder
                .toSearchQuery(request);

        var hits = operations.search(query, JobDocument.class);

        var results = hits.getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .toList();

        var page = SearchHitSupport.searchPageFor(
                hits,
                PageRequest.of(request.getPage(), request.getSize())
        );

        var pagination = new Pagination(
                page.getNumber(),
                page.getNumberOfElements(),
                page.getTotalElements(),
                page.getTotalPages()
        );

        return new JobSearchResponse(
                results,
                pagination,
                hits.getExecutionDuration().toMillis()
        );
    }
}

