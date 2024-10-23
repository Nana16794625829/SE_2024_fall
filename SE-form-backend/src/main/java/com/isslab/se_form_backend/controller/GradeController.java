package com.isslab.se_form_backend.controller;

import com.isslab.se_form_backend.entity.GradeEntity;
import com.isslab.se_form_backend.model.Status;
import com.isslab.se_form_backend.service.AbstractGradeService;
import com.isslab.se_form_backend.service.impl.MockGradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeController {
    private final AbstractGradeService gradeService;

    public GradeController(AbstractGradeService gradeService) {
        this.gradeService = gradeService;
    }

    /*
     * 匯出日間部同學成績
     * */
    @GetMapping("/export/undergraduate/{week}")
    public ResponseEntity<?> exportAllUndergraduatesGrades(@PathVariable String week) {
        gradeService.exportAllUndergraduatesGrades(week);
        return ResponseEntity.ok(statusOk());
    }

    /*
    * 匯出在職同學成績
    * */
    @GetMapping("/export/onService/{week}")
    public ResponseEntity<?> exportAllOnServiceGrades(@PathVariable String week) {
        gradeService.exportAllOnServiceGrades(week);
        return ResponseEntity.ok(statusOk());
    }

    @PostMapping("")
    public ResponseEntity<?> createGradeTable() {
        List<GradeEntity> gradeTable = gradeService.createGradeTable();
//        return ResponseEntity.ok(statusOk());
        return ResponseEntity.ok(gradeTable);
    }

    private static Status statusOk(){
        return Status.builder().status("200").response("ok").build();
    }
}
