package com.isslab.se_form_backend.service;

import com.isslab.se_form_backend.model.FormScoreRecord;

import java.text.ParseException;
import java.util.List;

public interface IFormService {
    void saveFormAndReviews(FormScoreRecord form) throws ParseException;
    FormEntity getFormById(Long id);
    List<ReviewEntity> getFormReviewByFormId(Long formId);
    List<ReviewEntity> getFormReviews();
}