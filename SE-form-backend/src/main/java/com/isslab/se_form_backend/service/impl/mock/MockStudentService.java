package com.isslab.se_form_backend.service.impl.mock;

import com.isslab.se_form_backend.service.AbstractStudentService;

public class MockStudentService extends AbstractStudentService {
    @Override
    public boolean isReviewer(String studentId) {
        return studentId.equals("113525009");
    }

    @Override
    public boolean isPresenter(String studentId) {
        return studentId.equals("113500001");
    }
}
