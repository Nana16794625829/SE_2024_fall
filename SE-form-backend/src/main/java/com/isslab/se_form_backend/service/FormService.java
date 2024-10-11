package com.isslab.se_form_backend.service;

import com.isslab.se_form_backend.entity.FormEntity;
import com.isslab.se_form_backend.entity.ReviewEntity;
import com.isslab.se_form_backend.entity.ScoreEntity;
import com.isslab.se_form_backend.model.Form;
import com.isslab.se_form_backend.model.FormLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Slf4j
public class FormService {

    public FormLog submitForm(Form form) {
        log.info(form.toString());
        return FormLog.builder().studentId(form.getStudentId()).log(form.getReviews().toString()).build();
    }

    public FormEntity getFormById(Long id) {
        Date now = new Date();
        return FormEntity.builder().id(id).reviewerId("113522010").reviewDate(now).comment(null).build();
    }

    public List<ReviewEntity> getFormReviewByFormId(Long formId) {
        ReviewEntity review1 = ReviewEntity.builder().id(1L).formId(1L).reviewerId("113522010").score("A").presenterId("113500001").build();
        ReviewEntity review2 = ReviewEntity.builder().id(2L).formId(1L).reviewerId("113522010").score("B").presenterId("113500002").build();
        ReviewEntity review3 = ReviewEntity.builder().id(3L).formId(1L).reviewerId("113522010").score("B").presenterId("113500003").build();
        ReviewEntity review4= ReviewEntity.builder().id(4L).formId(2L).reviewerId("113522099").score("B").presenterId("113500001").build();
        ReviewEntity review5= ReviewEntity.builder().id(5L).formId(2L).reviewerId("113522099").score("C").presenterId("113500002").build();
        ReviewEntity review6= ReviewEntity.builder().id(6L).formId(2L).reviewerId("113522099").score("B").presenterId("113500003").build();

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
