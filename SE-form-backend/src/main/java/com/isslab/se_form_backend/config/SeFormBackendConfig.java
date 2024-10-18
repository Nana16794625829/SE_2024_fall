package com.isslab.se_form_backend.config;

import com.isslab.se_form_backend.controller.*;
import com.isslab.se_form_backend.repository.GradeRepository;
import com.isslab.se_form_backend.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.isslab.se_form_backend.repository")
public class SeFormBackendConfig {

    @Bean
    public FormService formService() {
        return new FormService();
    }

    @Bean
    public FormController formController(FormService formService) {
        return new FormController(formService);
    }

    @Bean
    public StudentService studentService() {
        return new StudentService();
    }

    @Bean
    public StudentController studentController(StudentService studentService) {
        return new StudentController(studentService);
    }

    @Bean
    public PresenterService presenterService() {
        return new PresenterService();
    }

    @Bean
    public PresenterController presenterController(PresenterService presenterService) {
        return new PresenterController(presenterService);
    }

    @Bean
    public GradesToCSVService gradesToCSVService() {
        return new GradesToCSVService();
    }

    @Bean
    public GradeService gradeService(GradesToCSVService gradesToCSVService, FormService formService, GradeRepository gradeRepository) {
        return new GradeService(gradesToCSVService, formService, gradeRepository);
    }

    @Bean
    public GradeController gradeController(GradeService gradeService) {
        return new GradeController(gradeService);
    }

}


