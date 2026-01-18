package com.jobbridge.job.search.util;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.search.*;
import java.util.List;

public class ElasticsearchUtil {

    public static Suggester buildCompletionSuggester(
            String suggestName,
            String field,
            String prefix,
            int limit
    ) {
        var fuzziness = SuggestFuzziness.of(b -> b
                .fuzziness(JobConstants.Fuzzy.LEVEL)
                .prefixLength(JobConstants.Fuzzy.PREFIX_LENGTH)
        );

        var completion = CompletionSuggester.of(b -> b
                .field(field)
                .size(limit)
                .fuzzy(fuzziness)
                .skipDuplicates(true)
        );

        var fieldSuggester = FieldSuggester.of(b -> b
                .prefix(prefix)
                .completion(completion)
        );

        return Suggester.of(b -> b
                .suggesters(suggestName, fieldSuggester)
        );
    }

    public static Query buildTermQuery(String field, String value) {
        var term = TermQuery.of(b -> b
                .field(field)
                .value(value)
                .caseInsensitive(true)
        );

        return Query.of(b -> b.term(term));
    }

    public static Query buildRangeQuery(
            String field,
            Double from,
            Double to
    ) {
        var range = RangeQuery.of(b -> b
                .number(n -> n
                        .field(field)
                        .gte(from)
                        .lte(to)
                )
        );

        return Query.of(b -> b.range(range));
    }

    public static Query buildMultiMatchQuery(
            List<String> fields,
            String searchTerm
    ) {
        var multi = MultiMatchQuery.of(b -> b
                .query(searchTerm)
                .fields(fields)
                .fuzziness(JobConstants.Fuzzy.LEVEL)
                .prefixLength(JobConstants.Fuzzy.PREFIX_LENGTH)
                .operator(Operator.And)
        );

        return Query.of(b -> b.multiMatch(multi));
    }
}
