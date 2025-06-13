package com.isslab.se_form_backend.service.impl.mock;

import com.isslab.se_form_backend.entity.FormScoreRecordEntity;
import com.isslab.se_form_backend.helper.GradeMockDataLoader;
import com.isslab.se_form_backend.service.AbstractGradeService;
import com.isslab.se_form_backend.service.AbstractStudentRoleService;
import com.isslab.se_form_backend.service.AbstractStudentService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;


@Slf4j
public class MockGradeService extends AbstractGradeService {

    public MockGradeService(AbstractStudentRoleService reviewerService, AbstractStudentRoleService presenterService, AbstractStudentService studentService) {
        this.reviewerService = reviewerService;
        this.presenterService = presenterService;
        this.studentService = studentService;
    }

    @Override
    public void saveGradeToStudent(String studentId, String week, double grade) {
        AbstractStudentRoleService roleService = getServiceByRole(studentId);
        roleService.saveGradeToStudent(studentId, week, grade);
    }

    @Override
    public double getGradeByIdAndWeek(String studentId, String week) {
        AbstractStudentRoleService roleService = getServiceByRole(studentId);
        return roleService.getGradeByIdAndWeek(studentId, week);
    }

    @Override
    public void updateGradeByIdAndWeek(String studentId, String week, double grade) {
        AbstractStudentRoleService roleService = getServiceByRole(studentId);
        roleService.updateGradeByIdAndWeek(studentId, week, grade);
    }

    @Override
    protected List<FormScoreRecordEntity> loadFormScoreRecords() {
        return GradeMockDataLoader.loadFromCsv("/mock/sample.csv");
    }

}