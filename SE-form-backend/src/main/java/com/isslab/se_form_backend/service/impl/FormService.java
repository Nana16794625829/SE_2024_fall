package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.entity.FormEntity;
import com.isslab.se_form_backend.entity.ReviewEntity;
import com.isslab.se_form_backend.model.Form;
import com.isslab.se_form_backend.model.FormLog;
import com.isslab.se_form_backend.service.IFormService;

import java.util.List;

public class FormService implements IFormService {

    @Override
    public FormLog submitForm(Form form) {
        return null;
    }

    @Override
    public FormEntity getFormById(Long id) {
        return null;
    }

    @Override
    public List<ReviewEntity> getFormReviewByFormId(Long formId) {
        return null;
    }
}