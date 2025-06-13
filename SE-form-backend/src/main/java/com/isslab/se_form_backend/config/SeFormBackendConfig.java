package com.isslab.se_form_backend.config;

import com.isslab.se_form_backend.helper.FormScoreCsvImporter;
import com.isslab.se_form_backend.repository.FormScoreRecordRepository;
import com.isslab.se_form_backend.repository.ReviewerRepository;
import com.isslab.se_form_backend.service.AbstractGradeService;
import com.isslab.se_form_backend.service.AbstractStudentRoleService;
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

    private final Boolean MOCK = Boolean.TRUE;

    @Bean
    public AbstractStudentService studentService(){
        if (MOCK) {
            return new MockStudentService();
        } else {
            return new StudentService();
        }
    }

    @Bean
    public AbstractStudentRoleService reviewerService(ReviewerRepository reviewerRepository){
//        if (MOCK) {
//            return new MockReviewerService();
//        }
        return new ReviewerService(reviewerRepository);
    }

    @Bean
    public AbstractStudentRoleService presenterService(){
        if (MOCK) {
            return new MockPresenterService();
        }
        return new PresenterService();
    }

    @Bean
    public AbstractGradeService gradeService(AbstractStudentRoleService reviewerService, AbstractStudentRoleService presenterService, AbstractStudentService studentService){
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


