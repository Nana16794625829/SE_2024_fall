package com.isslab.se_form_backend.config;

import com.isslab.se_form_backend.helper.FormScoreCsvImporter;
import com.isslab.se_form_backend.repository.FormScoreRecordRepository;
import com.isslab.se_form_backend.service.AbstractGradeService;
import com.isslab.se_form_backend.service.AbstractPresenterService;
import com.isslab.se_form_backend.service.AbstractReviewerService;
import com.isslab.se_form_backend.service.AbstractStudentService;
import com.isslab.se_form_backend.service.impl.*;
import com.isslab.se_form_backend.service.impl.mock.MockGradeService;
import com.isslab.se_form_backend.service.impl.mock.MockPresenterService;
import com.isslab.se_form_backend.service.impl.mock.MockReviewerService;
import com.isslab.se_form_backend.service.impl.mock.MockStudentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.isslab.se_form_backend.repository")
public class SeFormBackendConfig {

    private final Boolean MOCK = Boolean.FALSE;

    @Bean
    public AbstractStudentService studentService(){
        if (MOCK) {
            return new MockStudentService();
        } else {
            return new StudentService();
        }
    }

    @Bean
    public AbstractReviewerService reviewerService(){
        if (MOCK) {
            return new MockReviewerService();
        }
        return new ReviewerService();
    }

    @Bean
    public AbstractPresenterService presenterService(){
        if (MOCK) {
            return new MockPresenterService();
        }
        return new PresenterService();
    }

    @Bean
    public AbstractGradeService gradeService(AbstractReviewerService reviewerService, AbstractPresenterService presenterService, AbstractStudentService studentService){
        if (MOCK) {
            return new MockGradeService(reviewerService, presenterService, studentService);
        } else {
            return new GradeService();
        }
    }

    @Bean
    public FormScoreRecordService formScoreRecordService(FormScoreRecordRepository formScoreRecordRepository){
        return new FormScoreRecordService(formScoreRecordRepository);
    }

    @Bean
    public FormScoreCsvImporter formScoreCsvImporter(FormScoreRecordRepository formScoreRecordRepository){
        return new FormScoreCsvImporter(formScoreRecordRepository);
    }

}


