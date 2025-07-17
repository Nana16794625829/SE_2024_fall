package com.isslab.se_form_backend.controller;

import com.isslab.se_form_backend.helper.ResponseStatus;
import com.isslab.se_form_backend.model.FormSubmission;
import com.isslab.se_form_backend.service.impl.FormProcessingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/form-processing")
public class FormProcessingController {

    private final FormProcessingService formProcessingService;

    public FormProcessingController(FormProcessingService formProcessingService) {
        this.formProcessingService = formProcessingService;
    }

    @PostMapping("/")
    public ResponseEntity<?> submit(@RequestBody FormSubmission formSubmission) {
        formProcessingService.process(formSubmission);

        return ResponseEntity.ok(ResponseStatus.statusOk());
    }
}
