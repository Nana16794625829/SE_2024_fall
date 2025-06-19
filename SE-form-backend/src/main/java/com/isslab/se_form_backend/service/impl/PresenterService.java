package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.repository.PresenterRepository;
import com.isslab.se_form_backend.service.AbstractStudentRoleService;

public class PresenterService extends AbstractStudentRoleService {

    private final PresenterRepository repository;

    public PresenterService(PresenterRepository repository){
        this.repository = repository;
    }

    @Override
    public void saveGradeToStudent(String studentId, String week, double grade) {

    }

    @Override
    public double getGradeByIdAndWeek(String studentId, String week) {
        return 0;
    }

    @Override
    public void updateGradeByIdAndWeek(String studentId, String week, double grade) {

    }

    @Override
    public void deleteGradeByIdAndWeek(String studentId, String week) {
        repository.deleteByPresenterIdAndWeek(studentId, week);
    }
}
