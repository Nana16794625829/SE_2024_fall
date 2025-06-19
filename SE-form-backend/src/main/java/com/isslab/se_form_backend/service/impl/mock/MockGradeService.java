package com.isslab.se_form_backend.service.impl.mock;

import com.isslab.se_form_backend.service.AbstractFormScoreRecordService;
import com.isslab.se_form_backend.service.AbstractGradeService;
import com.isslab.se_form_backend.service.AbstractStudentRoleService;
import com.isslab.se_form_backend.service.AbstractStudentService;
import com.isslab.se_form_backend.service.impl.FormScoreRecordService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class MockGradeService extends AbstractGradeService {

    public MockGradeService(AbstractStudentRoleService reviewerService, AbstractStudentRoleService presenterService, AbstractStudentService studentService, AbstractFormScoreRecordService formScoreRecordService) {
        this.reviewerService = reviewerService;
        this.presenterService = presenterService;
        this.studentService = studentService;
        this.formScoreRecordService = formScoreRecordService;
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
    public void deleteGradeByIdAndWeek(String studentId, String week) {

    }
}