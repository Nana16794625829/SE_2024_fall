package com.isslab.se_form_backend.controller;

import com.isslab.se_form_backend.model.StudentInformation;
import com.isslab.se_form_backend.service.impl.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<?> getStudentInformation(@PathVariable String studentId) {
        StudentInformation studentInfo = studentService.getStudentInformation(studentId);
        return ResponseEntity.ok(studentInfo);
    }

}
