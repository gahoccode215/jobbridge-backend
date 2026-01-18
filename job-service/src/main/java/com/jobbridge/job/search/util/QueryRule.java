package com.jobbridge.job.search.util;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.jobbridge.job.search.dto.JobSearchRequest;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public record QueryRule(
        Predicate<JobSearchRequest> predicate,
        Function<JobSearchRequest, Query> function
) {

    public static QueryRule of(
            Predicate<JobSearchRequest> predicate,
            Function<JobSearchRequest, Query> function
    ) {
        return new QueryRule(predicate, function);
    }

    public Optional<Query> build(JobSearchRequest parameters) {
        return Optional.of(parameters)
                .filter(this.predicate())
                .map(this.function());
    }
}
