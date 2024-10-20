package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.entity.GradeEntity;
import com.isslab.se_form_backend.model.Grade;
import com.isslab.se_form_backend.repository.GradeRepository;
import com.isslab.se_form_backend.service.AbstractGradeService;
import com.isslab.se_form_backend.service.IFormService;

import java.util.List;

public class GradeService extends AbstractGradeService {
    private final GradeRepository gradeRepository;
    public GradeService(GradesToCSVService gradesToCSVService, IFormService formService, GradeRepository gradeRepository) {
        super(gradesToCSVService, formService);
        this.gradeRepository = gradeRepository;
    }

    @Override
    protected List<Grade> getUndergraduatesGradesByWeek(String week) {
        //TODO: List<Grade> grades = gradeRepository.getGradesByWeek(week);

        Grade grade1 = Grade.builder().studentId("113000001").grade(86.41).build();
        Grade grade2 = Grade.builder().studentId("113000002").grade(93.22).build();
        Grade grade3 = Grade.builder().studentId("113000003").grade(93.22).build();
        Grade grade4 = Grade.builder().studentId("113000004").grade(75).build();
        Grade grade5 = Grade.builder().studentId("113000005").grade(60).build();

        return List.of(grade1, grade2, grade3, grade4, grade5);
    }

    @Override
    protected List<Grade> getOnServiceGradesByWeek(String week) {
        Grade grade1 = Grade.builder().studentId("113500001").grade(80).build();
        Grade grade2 = Grade.builder().studentId("113500002").grade(78.41).build();
        Grade grade3 = Grade.builder().studentId("113500003").grade(88.5).build();
        return List.of(grade1, grade2, grade3);
    }

    @Override
    protected void save(GradeEntity gradeEntity) {
        gradeRepository.save(gradeEntity);
    }

    @Override
    protected void updateReviewerDetailByReviewerIdAndPresenterId(String reviewerId, String presenterId, double standardDeviation, double zScore, double reviewerGrade, Boolean outlier, int round) {
        gradeRepository.updateReviewerDetailByReviewerIdAndPresenterId(reviewerId, presenterId, standardDeviation, zScore, reviewerGrade, outlier, 1);
    }

    @Override
    protected void updatePresenterGradeByPresenterId(double presenterGrade, String presenterId) {
        gradeRepository.updatePresenterGradeByPresenterId(presenterGrade, presenterId);
    }
}
