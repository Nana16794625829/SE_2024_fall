package com.isslab.se_form_backend.controller;

import com.isslab.se_form_backend.model.Grade;
import com.isslab.se_form_backend.model.Status;
import com.isslab.se_form_backend.service.GradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping("/undergraduate/{week}")
    public ResponseEntity<?> getAllUndergraduatesGrades(@PathVariable String week) {
        gradeService.getAllUndergraduatesGrades(week);
        Status status = Status.builder().status("200").response("ok").build();
        return ResponseEntity.ok(status);
    }

    @GetMapping("/onService/{week}")
    public ResponseEntity<?> getAllOnServiceGrades(@PathVariable String week) {
        gradeService.getAllOnServiceGrades(week);
        Status status = Status.builder().status("200").response("ok").build();
        return ResponseEntity.ok(status);
    }

    @PostMapping("")
    public ResponseEntity<?> createGradeTable() {
        gradeService.createGradeTable();
        Status status = Status.builder().status("200").response("ok").build();
        return ResponseEntity.ok(status);
    }
}
