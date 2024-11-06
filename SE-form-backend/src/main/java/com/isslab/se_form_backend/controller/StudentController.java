package com.isslab.se_form_backend.controller;

import com.isslab.se_form_backend.model.Status;
import com.isslab.se_form_backend.model.StudentInformation;
import com.isslab.se_form_backend.service.impl.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/addStudents")
    public ResponseEntity<?> addStudents(@RequestBody StudentInformation studentInformation) {
        studentService.addStudents(studentInformation);
        return ResponseEntity.ok(statusOk());
    }

    private static Status statusOk(){
        return Status.builder().status("200").response("ok").build();
    }
}
