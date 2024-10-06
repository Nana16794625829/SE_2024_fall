package com.isslab.se_form_backend.service;

import com.isslab.se_form_backend.model.Form;
import com.isslab.se_form_backend.model.FormLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
public class FormService {

    public FormLog submitForm(Form form) {
        log.info(form.toString());
        return FormLog.builder().studentId(form.getStudentId()).log(form.getReviews().toString()).build();
    }
}
