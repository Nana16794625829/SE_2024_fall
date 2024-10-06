package com.isslab.se_form_backend.service;

import com.isslab.se_form_backend.model.StudentInformation;

public class StudentService {

    public StudentInformation getStudentInformation(String studentId) {
        return StudentInformation
                .builder()
                .studentId(studentId)
                .name("Nana-test")
                .email("123@com")
                .build();
    }
}
