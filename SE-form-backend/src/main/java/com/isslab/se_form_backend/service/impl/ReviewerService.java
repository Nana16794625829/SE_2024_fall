package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.entity.ReviewerEntity;
import com.isslab.se_form_backend.repository.ReviewerRepository;
import com.isslab.se_form_backend.service.AbstractStudentRoleService;

public class ReviewerService extends AbstractStudentRoleService {

    private final ReviewerRepository repository;

    public ReviewerService(ReviewerRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveGradeToStudent(String studentId, String week, double grade) {
        ReviewerEntity reviewerEntity = new ReviewerEntity();

        reviewerEntity.setReviewerId(studentId);
        reviewerEntity.setWeek(week);
        reviewerEntity.setGrade(grade);

        repository.save(reviewerEntity);
    }

    @Override
    public double getGradeByIdAndWeek(String studentId, String week) {
        return 0;
    }

    @Override
    public void updateGradeByIdAndWeek(String studentId, String week, double grade) {

    }

    @Override
    public void deleteGradeByIdAndWeek(String studentId, String week) {
        repository.deleteByReviewerIdAndWeek(studentId, week);
    }
}
