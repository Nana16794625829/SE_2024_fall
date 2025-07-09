package com.isslab.se_form_backend.config;

import com.isslab.se_form_backend.helper.FormScoreCsvImporter;
import com.isslab.se_form_backend.helper.FormSubmissionImporter;
import com.isslab.se_form_backend.repository.*;
import com.isslab.se_form_backend.service.*;
import com.isslab.se_form_backend.service.impl.*;
import com.isslab.se_form_backend.service.impl.mock.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.isslab.se_form_backend.repository")
public class SeFormBackendConfig {

    private final Boolean MOCK = Boolean.FALSE;

    @Bean
    public AbstractStudentService studentService(StudentRepository studentRepository){
        if (MOCK) {
            return new MockStudentService();
        }
        return new StudentService(studentRepository);
    }

    @Bean
    public AbstractStudentRoleService reviewerService(ReviewerRepository reviewerRepository){
        if (MOCK) {
            return new MockReviewerService();
        }
        return new ReviewerService(reviewerRepository);
    }

    @Bean
    public AbstractStudentRoleService presenterServiceAbstract(PresenterRepository presenterRepository){
        if (MOCK) {
            return new MockPresenterService();
        }
        return new PresenterService(presenterRepository);
    }

    @Bean
    public AbstractGradeService gradeService(AbstractStudentRoleService reviewerService,
                                             AbstractStudentRoleService presenterService,
                                             AbstractStudentService studentService,
                                             AbstractFormScoreRecordService formScoreRecordService){
        if (MOCK) {
            return new MockGradeService(reviewerService, presenterService, studentService, formScoreRecordService);
        }
        return new GradeService(reviewerService, presenterService, studentService, formScoreRecordService);
    }

    @Bean
    public AbstractFormScoreRecordService formScoreRecordService(FormScoreRecordRepository formScoreRecordRepository,
                                                                 AbstractFormSubmissionService formSubmissionService){
        if (MOCK) {
            return new MockFormScoreRecordService();
        }
        return new FormScoreRecordService(formScoreRecordRepository, formSubmissionService);
    }

    @Bean
    public FormScoreCsvImporter formScoreCsvImporter(FormScoreRecordRepository formScoreRecordRepository){
        return new FormScoreCsvImporter(formScoreRecordRepository);
    }

    @Bean
    public FormSubmissionImporter formSubmissionImporter(FormSubmissionRepository formSubmissionRepository){
        return new FormSubmissionImporter(formSubmissionRepository);
    }

    @Bean
    public AbstractFormSubmissionService formSubmissionService(AbstractFormScoreRecordService formScoreRecordService, FormSubmissionRepository formSubmissionRepository){
        if (MOCK) {
            return new MockFormSubmissionService();
        }
        return new FormSubmissionService(formScoreRecordService, formSubmissionRepository);
    }

    @Bean
    public PresenterService presenterService(PresenterRepository presenterRepository) {
        return new PresenterService(presenterRepository);
    }

}


