package com.isslab.se_form_backend.controller;

import com.isslab.se_form_backend.model.Grade;
import com.isslab.se_form_backend.service.GradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping("/undergraduate")
    public ResponseEntity<?> getAllUndergraduatesGrades() {
        List<Grade> grades = this.gradeService.getAllUndergraduatesGrades();
        return ResponseEntity.ok(grades);
    }

    @GetMapping("/onService")
    public ResponseEntity<?> getAllOnServiceGrades() {
        List<Grade> grades = this.gradeService.getAllOnServiceGrades();
        return ResponseEntity.ok(grades);
    }
}
