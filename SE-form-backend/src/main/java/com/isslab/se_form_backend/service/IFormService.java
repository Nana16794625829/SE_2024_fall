package com.isslab.se_form_backend.service;

import com.isslab.se_form_backend.entity.FormEntity;
import com.isslab.se_form_backend.entity.ReviewEntity;
import com.isslab.se_form_backend.model.Form;
import com.isslab.se_form_backend.model.FormLog;

import java.util.List;

public interface IFormService {
    FormLog submitForm(Form form);
    FormEntity getFormById(Long id);
    List<ReviewEntity> getFormReviewByFormId(Long formId);
}