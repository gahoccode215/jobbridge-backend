package com.jobbridge.job.search.util;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.jobbridge.job.search.dto.JobSearchRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;

import java.util.List;
import java.util.Optional;

public class JobNativeQueryBuilder {

    private static final List<QueryRule> FILTER_RULES = List.of(
            JobQueryRules.LOCATION_QUERY,
            JobQueryRules.JOBTYPE_QUERY,
            JobQueryRules.SALARY_QUERY
    );

    private static final List<QueryRule> MUST_RULES = List.of(
            JobQueryRules.KEYWORD_QUERY
    );

    public static NativeQuery toSearchQuery(JobSearchRequest parameters) {

        var filterQueries = buildQueries(FILTER_RULES, parameters);
        var mustQueries = buildQueries(MUST_RULES, parameters);

        var boolQuery = BoolQuery.of(b -> b
                .filter(filterQueries)
                .must(mustQueries)
        );

        return NativeQuery.builder()
                .withQuery(Query.of(b -> b.bool(boolQuery)))
                .withPageable(
                        PageRequest.of(
                                parameters.getPage(),
                                parameters.getSize()
                        )
                )
                .withTrackTotalHits(true)
                .build();
    }

    public static NativeQuery toSuggestQuery(String prefix) {

        var suggester = ElasticsearchUtil.buildCompletionSuggester(
                JobConstants.Suggestion.SUGGEST_NAME,
                JobConstants.Suggestion.FIELD,
                prefix,
                10
        );

        return NativeQuery.builder()
                .withSuggester(suggester)
                .withMaxResults(0)
                .build();
    }

    private static List<Query> buildQueries(
            List<QueryRule> rules,
            JobSearchRequest parameters
    ) {
        return rules.stream()
                .map(q -> q.build(parameters))
                .flatMap(Optional::stream)
                .toList();
    }
}
