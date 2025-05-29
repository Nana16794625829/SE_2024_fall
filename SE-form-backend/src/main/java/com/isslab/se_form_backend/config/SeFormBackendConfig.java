package com.isslab.se_form_backend.config;

import com.isslab.se_form_backend.service.AbstractGradeService;
import com.isslab.se_form_backend.service.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableJpaRepositories(basePackages = "com.isslab.se_form_backend.repository")
public class SeFormBackendConfig {

    private final Boolean MOCK = Boolean.FALSE;

    @Bean
    public AbstractGradeService gradeService() {
        if (MOCK) {
            return new MockGradeService();
        } else {
            return new GradeService();
        }
    }
}


