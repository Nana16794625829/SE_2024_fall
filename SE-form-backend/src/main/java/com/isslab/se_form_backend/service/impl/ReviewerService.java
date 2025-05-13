package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.model.Student;
import com.isslab.se_form_backend.repository.ReviewerRepository;

public class ReviewerService {

    private final ReviewerRepository reviewerRepository;

    public ReviewerService(ReviewerRepository reviewerRepository) {
        this.reviewerRepository = reviewerRepository;
    }

    public void addReviewer(Student student) {
//        reviewerRepository.save(reviewerEntity);
    }

    private ReviewerEntity fromStudentInformation(Student student) {
        return ReviewerEntity.builder()
                .reviewerId(student.getStudentId())
                .build();
    }
}
