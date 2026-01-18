package com.jobbridge.job.search.util;

import java.util.List;
import java.util.Objects;

import static com.jobbridge.job.search.util.ElasticsearchUtil.*;
import static com.jobbridge.job.search.util.JobConstants.Fields.*;

public class JobQueryRules {

    public static final QueryRule KEYWORD_QUERY = QueryRule.of(
            req -> Objects.nonNull(req.getKeyword()),
            req -> buildMultiMatchQuery(
                    List.of(TITLE, DESCRIPTION, REQUIREMENTS),
                    req.getKeyword()
            )
    );

    public static final QueryRule LOCATION_QUERY = QueryRule.of(
            req -> Objects.nonNull(req.getLocation()),
            req -> buildTermQuery(LOCATION, req.getLocation())
    );

    public static final QueryRule JOBTYPE_QUERY = QueryRule.of(
            req -> Objects.nonNull(req.getJobType()),
            req -> buildTermQuery(JOB_TYPE, req.getJobType())
    );

    public static final QueryRule SALARY_QUERY = QueryRule.of(
            req -> req.getSalaryFrom() != null && req.getSalaryTo() != null,
            req -> buildRangeQuery(
                    SALARY,
                    req.getSalaryFrom(),
                    req.getSalaryTo()
            )
    );
}
