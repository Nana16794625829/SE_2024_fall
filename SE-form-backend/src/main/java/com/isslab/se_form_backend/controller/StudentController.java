package com.isslab.se_form_backend.controller;

import com.isslab.se_form_backend.model.Student;
import com.isslab.se_form_backend.service.AbstractStudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final AbstractStudentService studentService;

    public StudentController(AbstractStudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public ResponseEntity<Student> getStudentById(@PathVariable String id){
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

//    @PostMapping("/")
}
