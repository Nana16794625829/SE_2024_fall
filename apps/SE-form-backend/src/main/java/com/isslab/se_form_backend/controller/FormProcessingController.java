package com.isslab.se_form_backend.controller;

import com.isslab.se_form_backend.helper.ResponseStatus;
import com.isslab.se_form_backend.model.FormSubmission;
import com.isslab.se_form_backend.service.impl.FormProcessingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/form-processing")
public class FormProcessingController {

    private final FormProcessingService formProcessingService;

    public FormProcessingController(FormProcessingService formProcessingService) {
        this.formProcessingService = formProcessingService;
    }

    @PreAuthorize("hasRole('USER')") //當進來的用戶權限為 ROLE_USER
    @PostMapping("/")
    public ResponseEntity<?> submit(@RequestBody FormSubmission formSubmission,
                                    //此 authentication 是由 JwtAuthFilter 處理後的資訊
                                    Authentication authentication) {
        String username = authentication.getName();
        log.info("username: {}", username);
        formSubmission.setSubmitterId(username);

        formProcessingService.process(formSubmission);

        return ResponseEntity.ok(ResponseStatus.statusOk());
    }
}
