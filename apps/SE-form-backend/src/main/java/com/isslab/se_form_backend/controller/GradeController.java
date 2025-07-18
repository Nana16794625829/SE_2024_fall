package com.isslab.se_form_backend.controller;

import com.isslab.se_form_backend.helper.ResponseStatus;
import com.isslab.se_form_backend.model.Status;
import com.isslab.se_form_backend.service.AbstractGradeService;
import com.isslab.se_form_backend.service.impl.PresenterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/grade")
public class GradeController {

    private final AbstractGradeService gradeService;
    private final PresenterService presenterService;

    public GradeController(AbstractGradeService gradeService, PresenterService presenterService) {
        this.gradeService = gradeService;
        this.presenterService = presenterService;
    }

    @GetMapping("{id}/{week}")
    public ResponseEntity<List<Double>> getGradeByIdAndWeek(@PathVariable String id, @PathVariable String week){
        List<Double> grade = gradeService.getGradesByIdAndWeek(id, week);
        return ResponseEntity.ok(grade);
    }

    @PostMapping("/save")
    public ResponseEntity<Status> saveAllGradesByWeekAndPresenter(@RequestBody Map<String, String> body){
        int presenterOrder = Integer.parseInt(body.get("presenterOrder"));
        Map<String, Double> gradesMap = gradeService.calculateGradeBySinglePresenter(body.get("week"), presenterOrder);
        gradeService.saveAllGradesByWeekAndPresenter(body.get("week"), gradesMap);
        return ResponseEntity.ok(ResponseStatus.statusOk());
    }

    @PostMapping("/save/batch/{week}")
    public ResponseEntity<Status> saveAllGradesByWeek(@PathVariable String week){
        gradeService.calculateGradesByWeek(week);
        Set<String> presenterIds = presenterService.getPresenterIdsByWeek(week);
        gradeService.saveAllGradesByWeek(week, presenterIds);
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
