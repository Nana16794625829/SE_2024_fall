package com.isslab.se_form_backend.controller;

import com.isslab.se_form_backend.helper.ResponseStatus;
import com.isslab.se_form_backend.model.FormSubmission;
import com.isslab.se_form_backend.service.AbstractFormSubmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/form-submission")
public class FormSubmissionController {

    private final AbstractFormSubmissionService formSubmissionService;

    public FormSubmissionController(AbstractFormSubmissionService formSubmissionService) {
        this.formSubmissionService = formSubmissionService;
    }

    @PostMapping
    public ResponseEntity<?> submitFormSubmission(@RequestBody FormSubmission formSubmission) {
        formSubmissionService.save(formSubmission);

        return ResponseEntity.ok(ResponseStatus.statusOk());
    }
}
