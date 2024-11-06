package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.entity.StudentsEntity;
import com.isslab.se_form_backend.model.ClassType;
import com.isslab.se_form_backend.model.StudentInformation;
import com.isslab.se_form_backend.repository.StudentRepository;

public class StudentService {

    private final StudentRepository studentRepository;
    private final ReviewerService reviewerService;

    public StudentService(StudentRepository studentRepository, ReviewerService reviewerService) {
        this.studentRepository = studentRepository;
        this.reviewerService = reviewerService;
    }

    public StudentInformation getStudentInformation(String studentId) {
        return StudentInformation
                .builder()
                .studentId(studentId)
                .name("Nana-test")
                .email("123@com")
                .build();
    }

    public void addStudents(StudentInformation studentInformation) {
//        if (studentInformation.getClassType() == ClassType.REGULAR) reviewerService.addReviewers(studentInformation);

        StudentsEntity studentsEntity = fromStudentInformation(studentInformation);
        studentRepository.save(studentsEntity);
    }

    private static StudentsEntity fromStudentInformation(StudentInformation info) {
        return StudentsEntity.builder()
                .studentId(info.getStudentId())
                .name(info.getName())
                .email(info.getEmail())
                .classType(info.getClassType())
                .build();
    }

}
