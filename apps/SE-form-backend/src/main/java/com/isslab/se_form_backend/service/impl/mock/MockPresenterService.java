package com.isslab.se_form_backend.service.impl.mock;

import com.isslab.se_form_backend.model.GradeInput;
import com.isslab.se_form_backend.service.AbstractStudentRoleService;

import java.util.List;

public class MockPresenterService extends AbstractStudentRoleService {
    @Override
    public void saveAllGrades(List<GradeInput> gradeList) {

    }

    @Override
    public void saveGradeToStudent(String studentId, String week, double grade) {

    }

    @Override
    public List<Double> getGradesByIdAndWeek(String studentId, String week) {
        return List.of(80.0);
    }

    @Override
    public void deleteGradeByIdAndWeek(String studentId, String week) {

    }

    @Override
    public double getBasicGrade() {
        return 0;
    }
}
