package com.isslab.se_form_backend.service;

import com.isslab.se_form_backend.entity.FormEntity;
import com.isslab.se_form_backend.entity.ReviewEntity;
import com.isslab.se_form_backend.model.Form;
import com.isslab.se_form_backend.model.FormLog;
import com.isslab.se_form_backend.model.FormResponse;

import java.text.ParseException;
import java.util.List;

public interface IFormService {
    void saveFormAndReviews(FormResponse form) throws ParseException;
    FormEntity getFormById(Long id);
    List<ReviewEntity> getFormReviewByFormId(Long formId);
    List<ReviewEntity> getFormReviews();
}