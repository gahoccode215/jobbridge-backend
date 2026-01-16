package com.jobbridge.job.search.repository;

import com.jobbridge.job.search.document.JobDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface JobSearchRepository extends ElasticsearchRepository<JobDocument, Long> {

    List<JobDocument> findByTitleContainingOrDescriptionContaining(String title, String description);

    List<JobDocument> findByLocationContaining(String location);
}
