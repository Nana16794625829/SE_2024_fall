package com.isslab.se_form_backend.service;

import com.isslab.se_form_backend.model.Grade;

import java.util.List;

public class GradeService {

    public List<Grade> getAllUndergraduatesGrades() {
        Grade grade1 = Grade.builder().studentId("113000001").grade(86.41).build();
        Grade grade2 = Grade.builder().studentId("113000002").grade(93.22).build();
        Grade grade3 = Grade.builder().studentId("113000003").grade(93.22).build();
        Grade grade4 = Grade.builder().studentId("113000004").grade(75).build();
        Grade grade5 = Grade.builder().studentId("113000005").grade(60).build();

        return List.of(grade1, grade2, grade3, grade4, grade5);
    }

    public List<Grade> getAllOnServiceGrades() {
        Grade grade1 = Grade.builder().studentId("113500001").grade(80).build();
        Grade grade2 = Grade.builder().studentId("113500002").grade(78.41).build();
        Grade grade3 = Grade.builder().studentId("113500003").grade(88.5).build();

        return List.of(grade1, grade2, grade3);
    }
}
