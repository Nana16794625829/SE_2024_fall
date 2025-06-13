package com.isslab.se_form_backend.controller;

import com.isslab.se_form_backend.entity.FormScoreRecordEntity;
import com.isslab.se_form_backend.helper.ResponseStatus;
import com.isslab.se_form_backend.model.Status;
import com.isslab.se_form_backend.service.impl.FormScoreRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/form-scores")
public class FormScoreRecordController {

    private final FormScoreRecordService formScoreRecordService;

    public FormScoreRecordController(FormScoreRecordService formScoreRecordService) {
        this.formScoreRecordService = formScoreRecordService;
    }

    @GetMapping("")
    public ResponseEntity<List<FormScoreRecordEntity>> getAllFormScoreRecords() {
        return ResponseEntity.ok(formScoreRecordService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<FormScoreRecordEntity> getFormScoreRecordById(@PathVariable Long id) {
        return ResponseEntity.ok(formScoreRecordService.getById(id));
    }

    @PostMapping("")
    public ResponseEntity<FormScoreRecordEntity> addFormScoreRecord(@RequestBody FormScoreRecordEntity newFormScoreRecord) {
        return ResponseEntity.ok(formScoreRecordService.create(newFormScoreRecord));
    }

    @PutMapping("{id}")
    public ResponseEntity<FormScoreRecordEntity> updateFormScoreRecordById(@PathVariable Long id, @RequestBody FormScoreRecordEntity newFormScoreRecord) {
        return ResponseEntity.ok(formScoreRecordService.update(id, newFormScoreRecord));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Status> deleteFormScoreRecordById(@PathVariable Long id) {
        formScoreRecordService.delete(id);
        return ResponseEntity.ok(ResponseStatus.statusOk());
    }
}
