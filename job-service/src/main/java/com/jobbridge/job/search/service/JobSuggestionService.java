package com.jobbridge.job.search.service;

import com.jobbridge.job.search.util.JobConstants;
import com.jobbridge.job.search.util.JobNativeQueryBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.suggest.response.Suggest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class JobSuggestionService {

    private final ElasticsearchOperations operations;

    public List<String> suggest(String keyword) {

        var query = JobNativeQueryBuilder.toSuggestQuery(keyword);

        var hits = operations.search(
                query,
                Object.class,
                JobConstants.Index.SUGGESTION
        );

        return Optional.ofNullable(hits.getSuggest())
                .map(s -> s.getSuggestion(JobConstants.Suggestion.SUGGEST_NAME))
                .stream()
                .map(Suggest.Suggestion::getEntries)
                .flatMap(Collection::stream)
                .map(Suggest.Suggestion.Entry::getOptions)
                .flatMap(Collection::stream)
                .map(Suggest.Suggestion.Entry.Option::getText)
                .toList();
    }
}

