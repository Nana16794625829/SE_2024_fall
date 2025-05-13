package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.model.Student;
import com.isslab.se_form_backend.repository.StudentRepository;

public class StudentService {

    private final StudentRepository studentRepository;
    private final ReviewerService reviewerService;

    public StudentService(StudentRepository studentRepository, ReviewerService reviewerService) {
        this.studentRepository = studentRepository;
        this.reviewerService = reviewerService;
    }

    public Student getStudentInformation(String studentId) {
        return Student
                .builder()
                .studentId(studentId)
                .name("Nana-test")
                .email("123@com")
                .build();
    }

    public void addStudents(Student student) {
//        if (studentInformation.getClassType() == ClassType.DAY) reviewerService.addReviewers(studentInformation);

        StudentsEntity studentsEntity = fromStudentInformation(student);
        studentRepository.save(studentsEntity);
    }

    private static StudentsEntity fromStudentInformation(Student info) {
        return StudentsEntity.builder()
                .studentId(info.getStudentId())
                .name(info.getName())
                .email(info.getEmail())
                .classType(info.getClassType())
                .build();
    }

}
