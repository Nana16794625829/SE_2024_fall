package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.entity.ReviewerEntity;
import com.isslab.se_form_backend.model.StudentInformation;
import com.isslab.se_form_backend.repository.ReviewerRepository;

public class ReviewerService {

    private final ReviewerRepository reviewerRepository;

    public ReviewerService(ReviewerRepository reviewerRepository) {
        this.reviewerRepository = reviewerRepository;
    }

    public void addReviewer(StudentInformation studentInformation) {
//        reviewerRepository.save(reviewerEntity);
    }

    private ReviewerEntity fromStudentInformation(StudentInformation studentInformation) {
        return ReviewerEntity.builder()
                .reviewerId(studentInformation.getStudentId())
                .build();
    }
}
