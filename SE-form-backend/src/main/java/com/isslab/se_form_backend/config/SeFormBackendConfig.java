package com.isslab.se_form_backend.config;

import com.isslab.se_form_backend.helper.FormScoreCsvImporter;
import com.isslab.se_form_backend.repository.FormScoreRecordRepository;
import com.isslab.se_form_backend.service.AbstractGradeService;
import com.isslab.se_form_backend.service.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.isslab.se_form_backend.repository")
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

    @Bean
    public FormScoreRecordService formScoreRecordService(FormScoreRecordRepository formScoreRecordRepository) {
        return new FormScoreRecordService(formScoreRecordRepository);
    }

    @Bean
    public FormScoreCsvImporter formScoreCsvImporter(FormScoreRecordRepository formScoreRecordRepository) {
        return new FormScoreCsvImporter(formScoreRecordRepository);
    }

}


