package com.isslab.se_form_backend.service;

public abstract class AbstractReviewerService {
    public abstract void save(String studentId, String week, double grade);
    public abstract double getGradeByIdAndWeek(String studentId, String week);
}
