package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.entity.FormEntity;
import com.isslab.se_form_backend.entity.GradeEntity;
import com.isslab.se_form_backend.entity.ReviewEntity;
import com.isslab.se_form_backend.model.Form;
import com.isslab.se_form_backend.model.FormLog;
import com.isslab.se_form_backend.model.FormResponse;
import com.isslab.se_form_backend.service.IFormService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Slf4j
public class MockFormService implements IFormService {
    private static final ReviewEntity review1 = new ReviewEntity(1L, 1L, "A", 1, "113522990");
    private static final ReviewEntity review2 = new ReviewEntity(2L, 1L, "C", 1, "113522991");
    private static final ReviewEntity review3 = new ReviewEntity(3L, 1L,"C", 1, "113522992");
    private static final ReviewEntity review4 = new ReviewEntity(4L, 1L,"C", 1, "113522993");
    private static final ReviewEntity review5 = new ReviewEntity(5L, 1L,"C", 1, "113522994");
    private static final ReviewEntity review6 = new ReviewEntity(6L, 1L,"C", 1, "113522995");
    private static final ReviewEntity review7 = new ReviewEntity(7L, 1L,"C", 1, "113522996");
    private static final ReviewEntity review8 = new ReviewEntity(8L, 1L,"C", 1, "113522997");
    private static final ReviewEntity review9 = new ReviewEntity(9L, 1L,"C", 1, "113522998");
    private static final ReviewEntity review10 = new ReviewEntity(10L, 1L,"C", 1, "113522999");

    @Override
    public void saveFormAndReviews(FormResponse form) {
        log.info(form.toString());
    }

    @Override
    public FormEntity getFormById(Long id) {
        Date now = new Date();
        return new FormEntity(id, "113522010", "Nana", "xxx@gmail",null, now, "week2");
    }

    @Override
    public List<ReviewEntity> getFormReviewByFormId(Long formId) {


        List<ReviewEntity> reviewEntities = new ArrayList<>();
        reviewEntities.add(review1);
        reviewEntities.add(review2);
        reviewEntities.add(review3);
        reviewEntities.add(review4);
        reviewEntities.add(review5);
        reviewEntities.add(review6);
        reviewEntities.add(review7);
        reviewEntities.add(review8);
        reviewEntities.add(review9);
        reviewEntities.add(review10);

        return reviewEntities;
    }

    @Override
    public List<ReviewEntity> getFormReviews() {
        ReviewEntity review2_1 = new ReviewEntity(11L, 2L, "A", 2, "113522990");
        ReviewEntity review2_2 = new ReviewEntity(12L, 2L, "A", 2, "113522991");
        ReviewEntity review2_3 = new ReviewEntity(13L, 2L, "A", 2, "113522992");
        ReviewEntity review2_4 = new ReviewEntity(14L, 2L, "A", 2, "113522993");
        ReviewEntity review2_5 = new ReviewEntity(15L, 2L, "A", 2, "113522994");
        ReviewEntity review2_6 = new ReviewEntity(16L, 2L, "A", 2, "113522995");
        ReviewEntity review2_7 = new ReviewEntity(17L, 2L, "A", 2, "113522996");
        ReviewEntity review2_8 = new ReviewEntity(18L, 2L, "A", 2, "113522997"); // outlier
        ReviewEntity review2_9 = new ReviewEntity(19L, 2L, "A", 2, "113522998");
        ReviewEntity review2_10 = new ReviewEntity(20L, 2L, "A", 2, "113522999");

        List<ReviewEntity> reviewEntities = new ArrayList<>();

        //  打亂寫入順序，用來檢查是否會在計算成績前先正確排序
        reviewEntities.add(review1);

        reviewEntities.add(review4);
        reviewEntities.add(review5);
        reviewEntities.add(review6);
        reviewEntities.add(review7);
        reviewEntities.add(review8);
        reviewEntities.add(review9);
        reviewEntities.add(review10);

        reviewEntities.add(review2_1);
        reviewEntities.add(review2_2);
        reviewEntities.add(review2_3);
        reviewEntities.add(review2_4);
        reviewEntities.add(review2_5);

        reviewEntities.add(review2);
        reviewEntities.add(review3);

        reviewEntities.add(review2_6);
        reviewEntities.add(review2_7);
        reviewEntities.add(review2_8);
        reviewEntities.add(review2_9);
        reviewEntities.add(review2_10);

        reviewEntities.sort(Comparator.comparingLong(ReviewEntity::getId));

        return reviewEntities;
    }

//    @Override
//    public List<ReviewEntity> getFormReviewByFormId(Long formId) {
//        ReviewEntity review1 = new ReviewEntity(1L, 1L, "113522010", "A", "113500001");
//        ReviewEntity review2 = new ReviewEntity(2L, 1L, "113522010", "C", "113500002");
//        ReviewEntity review3 = new ReviewEntity(3L, 1L, "113522010", "B", "113500003");
//        ReviewEntity review4 = new ReviewEntity(4L, 1L, "113522099", "C", "113500001");
//        ReviewEntity review5 = new ReviewEntity(5L, 1L, "113522099", "B", "113500002");
//        ReviewEntity review6 = new ReviewEntity(6L, 1L, "113522099", "B", "113500003");
//
//        List<ReviewEntity> reviewEntities = new ArrayList<>();
//        reviewEntities.add(review1);
//        reviewEntities.add(review2);
//        reviewEntities.add(review3);
//        reviewEntities.add(review4);
//        reviewEntities.add(review5);
//        reviewEntities.add(review6);
//
//        return reviewEntities;
//    }
}
