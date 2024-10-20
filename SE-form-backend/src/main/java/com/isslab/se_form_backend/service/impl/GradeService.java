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
        return null;
    }

    @Override
    protected List<Grade> getOnServiceGradesByWeek(String week) {
        return null;
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
