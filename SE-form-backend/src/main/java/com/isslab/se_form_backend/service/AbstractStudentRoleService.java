package com.isslab.se_form_backend.service;

import com.isslab.se_form_backend.model.GradeInput;

import java.util.List;

public abstract class AbstractStudentRoleService {
    public abstract void saveAllGrades(List<GradeInput> gradeList);
    public abstract void saveGradeToStudent(String studentId, String week, double grade);
    public abstract List<Double> getGradesByIdAndWeek(String studentId, String week);
    public abstract void deleteGradeByIdAndWeek(String studentId, String week);
    public abstract double getBasicGrade();
}
