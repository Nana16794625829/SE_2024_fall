package com.isslab.se_form_backend.service.impl.mock;

import com.isslab.se_form_backend.service.AbstractStudentRoleService;

public class MockPresenterService extends AbstractStudentRoleService {
    @Override
    public void saveGradeToStudent(String studentId, String week, double grade) {

    }

    @Override
    public double getGradeByIdAndWeek(String studentId, String week) {
        return 80;
    }

    @Override
    public void updateGradeByIdAndWeek(String studentId, String week, double grade) {

    }

    @Override
    public void deleteGradeByIdAndWeek(String studentId, String week) {

    }
}
