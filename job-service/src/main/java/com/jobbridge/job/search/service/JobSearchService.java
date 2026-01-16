package com.jobbridge.job.search.service;

import com.jobbridge.job.search.document.JobDocument;
import com.jobbridge.job.search.repository.JobSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobSearchService {

    private final JobSearchRepository repository;

    public List<JobDocument> search(String keyword) {
        return repository.findByTitleContainingOrDescriptionContaining(keyword, keyword);
    }

    public List<JobDocument> searchByLocation(String location) {
        return repository.findByLocationContaining(location);
    }
}
