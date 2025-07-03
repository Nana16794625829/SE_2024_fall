package com.isslab.se_form_backend.service.impl.mock;

import com.isslab.se_form_backend.entity.ReviewerGradeEntity;
import com.isslab.se_form_backend.model.GradeInput;
import com.isslab.se_form_backend.service.AbstractStudentRoleService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MockReviewerService extends AbstractStudentRoleService {
    public MockReviewerService() {}


    @Override
    public void saveAllGrades(List<GradeInput> gradeList) {

    }

    @Override
    public void saveGradeToStudent(String studentId, String week, double grade) {
        log.info("saved grade to student: {}, week: {}, grade: {} ", studentId, week, grade);
    }

    @Override
    public List<Double> getGradesByIdAndWeek(String studentId, String week) {
        return List.of(60.0);
    }

    @Override
    public void deleteGradeByIdAndWeek(String studentId, String week) {

    }

    @Override
    public double getBasicGrade() {
        return 75;
    }

    public List<ReviewerGradeEntity> findNonAttendeeByWeek(String week){
        return List.of();
    }
}
