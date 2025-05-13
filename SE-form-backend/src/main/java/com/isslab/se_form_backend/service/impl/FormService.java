package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.model.FormScoreRecord;
import com.isslab.se_form_backend.repository.FormRepository;
import com.isslab.se_form_backend.repository.ReviewRepository;
import com.isslab.se_form_backend.service.IFormService;

import java.text.ParseException;
import java.util.List;

public class FormService implements IFormService {

    private final PresenterService presenterService;
    private final FormRepository formRepository;
    private final ReviewRepository reviewRepository;

    public FormService(PresenterService presenterService, FormRepository formRepository, ReviewRepository reviewRepository) {
        this.presenterService = presenterService;
        this.formRepository = formRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void saveFormAndReviews(FormScoreRecord form) throws ParseException {
        String week = form.getWeek();

        FormEntity formEntity = FormEntity
                .builder()
                .reviewerId(form.getReviewerId())
                .reviewerName(form.getReviewerName())
                .reviewerEmail(form.getReviewerEmail())
                .reviewDate(form.getReviewDate())
                .week(week)
                .comment(form.getComment())
                .build();
        formRepository.save(formEntity);

        List<String> scoreList = form.getScoreList();

        for (int i = 0; i < scoreList.size(); i++) {
            String score = scoreList.get(i);
            int presentOrder = i + 1;
            String presenterId = presenterService.getPresenterIdByWeekAndOrder(week, presentOrder);

            ReviewEntity reviewEntity = ReviewEntity
                    .builder()
                    .formId(formEntity.getId())
                    .score(score)
                    .presenterId(presenterId)
                    .presentOrder(presentOrder)
                    .build();

            reviewRepository.save(reviewEntity);
        }

    }

    @Override
    public FormEntity getFormById(Long id) {
        return null;
    }

    @Override
    public List<ReviewEntity> getFormReviewByFormId(Long formId) {
        return null;
    }

    @Override
    public List<ReviewEntity> getFormReviews() { return null; }
}