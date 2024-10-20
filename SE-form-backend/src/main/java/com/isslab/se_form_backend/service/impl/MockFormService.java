package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.entity.FormEntity;
import com.isslab.se_form_backend.entity.ReviewEntity;
import com.isslab.se_form_backend.model.Form;
import com.isslab.se_form_backend.model.FormLog;
import com.isslab.se_form_backend.service.IFormService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class MockFormService implements IFormService {

    @Override
    public FormLog submitForm(Form form) {
        log.info(form.toString());
        return FormLog.builder().studentId(form.getStudentId()).log(form.getReviews().toString()).build();
    }

    @Override
    public FormEntity getFormById(Long id) {
        Date now = new Date();
        return new FormEntity(id, "113522010", now, null);
    }

    @Override
    public List<ReviewEntity> getFormReviewByFormId(Long formId) {
        ReviewEntity review1 = new ReviewEntity(1L, 1L, "113522010", "A", "113500001");
        ReviewEntity review2 = new ReviewEntity(2L, 1L, "113522010", "C", "113500002");
        ReviewEntity review3 = new ReviewEntity(3L, 1L, "113522010", "B", "113500003");
        ReviewEntity review4 = new ReviewEntity(4L, 1L, "113522099", "C", "113500001");
        ReviewEntity review5 = new ReviewEntity(5L, 1L, "113522099", "B", "113500002");
        ReviewEntity review6 = new ReviewEntity(6L, 1L, "113522099", "B", "113500003");

        List<ReviewEntity> reviewEntities = new ArrayList<>();
        reviewEntities.add(review1);
        reviewEntities.add(review2);
        reviewEntities.add(review3);
        reviewEntities.add(review4);
        reviewEntities.add(review5);
        reviewEntities.add(review6);

        return reviewEntities;
    }
}
