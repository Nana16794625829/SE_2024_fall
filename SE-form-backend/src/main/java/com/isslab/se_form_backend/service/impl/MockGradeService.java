package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.model.Grade;
import com.isslab.se_form_backend.service.AbstractGradeService;
import com.isslab.se_form_backend.service.IFormService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MockGradeService extends AbstractGradeService {
    public MockGradeService(GradesToCSVService gradesToCSVService, IFormService formService) {
        super(gradesToCSVService, formService);
    }

    @Override
    protected List<Grade> getDayClassGradesByWeek(String week) {
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

    }

//    @Override
//    protected void updateReviewerDetailByReviewerIdAndPresenterId(String reviewerId, String presenterId, double standardDeviation, double zScore, double reviewerGrade, Boolean outlier, int round) {
//    }

    @Override
    protected void updatePresenterGradeByPresenterId(GradeEntity grade) {

    }

    @Override
    protected void updateReviewerGradesByReviewerId(GradeEntity grade) {

    }

}