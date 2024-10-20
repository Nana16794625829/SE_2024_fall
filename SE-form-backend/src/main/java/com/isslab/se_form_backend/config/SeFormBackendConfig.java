package com.isslab.se_form_backend.config;

import com.isslab.se_form_backend.controller.*;
import com.isslab.se_form_backend.repository.GradeRepository;
import com.isslab.se_form_backend.service.AbstractGradeService;
import com.isslab.se_form_backend.service.IFormService;
import com.isslab.se_form_backend.service.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.isslab.se_form_backend.repository")
public class SeFormBackendConfig {

    private Boolean MOCK = Boolean.TRUE;

    @Bean
    public IFormService formService() {
        if(MOCK.equals(Boolean.TRUE)){
            return new MockFormService();
        }else{
            return new FormService();
        }
    }

    @Bean
    public FormController formController(IFormService formService) {
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
        String  csvDir = "src/main/resources/output/";
        return new GradesToCSVService(csvDir);
    }

    @Bean
    public AbstractGradeService gradeService(GradesToCSVService gradesToCSVService, IFormService formService, GradeRepository gradeRepository) {
//        if(MOCK.equals(Boolean.TRUE)){
//            return new MockGradeService(gradesToCSVService, formService);
//        }else{
//            return new GradeService(gradesToCSVService, formService, gradeRepository);
//        }
            return new GradeService(gradesToCSVService, formService, gradeRepository);
    }

    @Bean
    public GradeController gradeController(AbstractGradeService gradeService) {
        return new GradeController(gradeService);
    }

}


