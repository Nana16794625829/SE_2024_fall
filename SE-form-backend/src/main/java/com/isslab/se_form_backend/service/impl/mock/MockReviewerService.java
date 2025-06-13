package com.isslab.se_form_backend.service.impl.mock;

import com.isslab.se_form_backend.service.AbstractStudentRoleService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MockReviewerService extends AbstractStudentRoleService {
    public MockReviewerService() {}


    @Override
    public void saveGradeToStudent(String studentId, String week, double grade) {
        log.info("saved grade to student: {}, week: {}, grade: {} ", studentId, week, grade);
    }

    @Override
    public double getGradeByIdAndWeek(String studentId, String week) {
        return 60;
    }

    @Override
    public void updateGradeByIdAndWeek(String studentId, String week, double grade) {

    }
}
