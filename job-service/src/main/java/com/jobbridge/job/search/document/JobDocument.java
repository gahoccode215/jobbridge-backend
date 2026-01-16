package com.jobbridge.job.search.document;

import com.jobbridge.job.job.enums.JobType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "jobs")
public class JobDocument {

    @Id
    private Long id;

    private String title;

    private String description;

    private String requirements;

    private String location;

    private Double salary;

    private JobType jobType;

    private String employerId;

    @Field(type = FieldType.Date)
    private LocalDate expiredAt;
}
