package com.isslab.se_form_backend.controller;

import com.isslab.se_form_backend.entity.FormEntity;
import com.isslab.se_form_backend.model.Form;
import com.isslab.se_form_backend.model.FormResponse;
import com.isslab.se_form_backend.model.Status;
import com.isslab.se_form_backend.service.IFormService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api")
public class FormController {

    private final IFormService formService;

    public FormController(IFormService formService) {
        this.formService = formService;
    }

    @PostMapping("/form")
    public ResponseEntity<?> submitForm(@RequestBody FormResponse body) throws ParseException {
        formService.saveFormAndReviews(body);
        Status status = Status.builder().status("200").response("ok").build();
        return ResponseEntity.ok(status);
    }
}
