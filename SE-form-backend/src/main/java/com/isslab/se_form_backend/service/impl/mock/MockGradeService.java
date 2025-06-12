package com.isslab.se_form_backend.service.impl.mock;

import com.isslab.se_form_backend.entity.FormScoreRecordEntity;
import com.isslab.se_form_backend.helper.GradeMockDataLoader;
import com.isslab.se_form_backend.service.AbstractGradeService;
import com.isslab.se_form_backend.service.AbstractPresenterService;
import com.isslab.se_form_backend.service.AbstractReviewerService;
import com.isslab.se_form_backend.service.AbstractStudentService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
public class MockGradeService extends AbstractGradeService {

    private final AbstractReviewerService reviewerService;
    private final AbstractPresenterService presenterService;
    private final AbstractStudentService studentService;

    public MockGradeService(AbstractReviewerService reviewerService, AbstractPresenterService presenterService, AbstractStudentService studentService) {
        this.reviewerService = reviewerService;
        this.presenterService = presenterService;
        this.studentService = studentService;
    }

    @Override
    public void saveGradeToStudent(String studentId, String week, double grade) {}

    @Override
    public double getGradeByIdAndWeek(String studentId, String week) {
        boolean isReviewer = studentService.isReviewer(studentId);
        boolean isPresenter = studentService.isPresenter(studentId);

        if(isReviewer) return reviewerService.getGradeByIdAndWeek(studentId, week);
        else if (isPresenter) return presenterService.getGradeByIdAndWeek(studentId, week);
        else throw new IllegalArgumentException("學生身份無法辨識: " + studentId);
    }

    @Override
    protected List<FormScoreRecordEntity> loadFormScoreRecords() {
        return GradeMockDataLoader.loadFromCsv("/mock/sample.csv");
    }
}