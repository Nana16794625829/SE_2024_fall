package com.isslab.se_form_backend.service.impl.mock;

import com.isslab.se_form_backend.service.AbstractReviewerService;

public class MockReviewerService extends AbstractReviewerService {
    public MockReviewerService() {}


    @Override
    public void save(String studentId, String week, double grade) {}

    @Override
    public double getGradeByIdAndWeek(String studentId, String week) {
        return 96.2;
    }
}
