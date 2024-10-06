package com.isslab.se_form_backend.config;

import com.isslab.se_form_backend.controller.FormController;
import com.isslab.se_form_backend.controller.PresenterController;
import com.isslab.se_form_backend.controller.StudentController;
import com.isslab.se_form_backend.service.FormService;
import com.isslab.se_form_backend.service.PresenterService;
import com.isslab.se_form_backend.service.StudentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
//@EnableJpaRepositories(basePackages = "com.isslab.se_form_backend.repository")
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
}


