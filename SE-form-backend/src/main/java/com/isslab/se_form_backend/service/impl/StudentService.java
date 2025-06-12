package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.service.AbstractStudentService;

public class StudentService extends AbstractStudentService {
    @Override
    public boolean isReviewer(String studentId) {
        return false;
    }

    @Override
    public boolean isPresenter(String studentId) {
        return false;
    }
}
