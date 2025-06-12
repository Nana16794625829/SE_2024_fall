package com.isslab.se_form_backend.service.impl.mock;

import com.isslab.se_form_backend.service.AbstractPresenterService;

public class MockPresenterService extends AbstractPresenterService {
    @Override
    public double getGradeByIdAndWeek(String studentId, String week) {
        return 80;
    }
}
