package com.isslab.se_form_backend.service;

public abstract class AbstractStudentRoleService {
    public abstract void saveGradeToStudent(String studentId, String week, double grade);
    public abstract double getGradeByIdAndWeek(String studentId, String week);
    public abstract void updateGradeByIdAndWeek(String studentId, String week, double grade);
}
