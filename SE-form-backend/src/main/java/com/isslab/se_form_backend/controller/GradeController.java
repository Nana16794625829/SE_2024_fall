package com.isslab.se_form_backend.controller;

import com.isslab.se_form_backend.helper.ResponseStatus;
import com.isslab.se_form_backend.model.Status;
import com.isslab.se_form_backend.service.AbstractGradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/grade")
public class GradeController {

    private final AbstractGradeService gradeService;

    public GradeController(AbstractGradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping("{id}/{week}")
    public ResponseEntity<Double> getGradeByIdAndWeek(@PathVariable String id, @PathVariable String week){
        double grade = gradeService.getGradeByIdAndWeek(id, week);
        return ResponseEntity.ok(grade);
    }

    @PostMapping("/save")
    public ResponseEntity<Status> saveAllGradesByWeek(@RequestBody Map<String, String> body){
        Map<String, Double> gradesMap = gradeService.calculateGrade(body.get("week"));
        gradeService.saveAllGradesByWeek(body.get("week"), gradesMap);
        return ResponseEntity.ok(ResponseStatus.statusOk());
    }

    @PutMapping("/update")
    public ResponseEntity<Status> setGradeById(@RequestBody Map<String, String> body){
        gradeService.saveGradeToStudent(body.get("id"), body.get("week"), Double.parseDouble(body.get("grade")));
        return ResponseEntity.ok(ResponseStatus.statusOk());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Status> deleteGradeByIdAndWeek(@RequestBody Map<String, String> body){
        gradeService.deleteGradeByIdAndWeek(body.get("id"), body.get("week"));
        return ResponseEntity.ok(ResponseStatus.statusOk());
    }
}
