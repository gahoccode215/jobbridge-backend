package com.jobbridge.job.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI jobServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Job Service API")
                        .version("1.0")
                        .description("API Documentation for JobBridge Job Service"));
    }
}
