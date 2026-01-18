package com.jobbridge.job.search.util;

import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

public class JobConstants {

    public static class Index {
        public static final IndexCoordinates JOB = IndexCoordinates.of("jobs");
        public static final IndexCoordinates SUGGESTION = IndexCoordinates.of("job-suggestions");
    }

    public static class Suggestion {
        public static final String FIELD = "suggest";
        public static final String SUGGEST_NAME = "job-suggest";
    }

    public static class Fields {
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String REQUIREMENTS = "requirements";
        public static final String LOCATION = "location";
        public static final String JOB_TYPE = "jobType";
        public static final String SALARY = "salary";
    }

    public static class Fuzzy {
        public static final String LEVEL = "1";
        public static final Integer PREFIX_LENGTH = 2;
    }
}
