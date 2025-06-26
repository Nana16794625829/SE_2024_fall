package com.isslab.se_form_backend.controller;

import com.isslab.se_form_backend.entity.FormScoreRecordEntity;
import com.isslab.se_form_backend.helper.ResponseStatus;
import com.isslab.se_form_backend.model.Status;
import com.isslab.se_form_backend.service.AbstractFormScoreRecordService;
import com.isslab.se_form_backend.service.impl.FormScoreRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/form-scores")
public class FormScoreRecordController {

    private final AbstractFormScoreRecordService formScoreRecordService;

    public FormScoreRecordController(AbstractFormScoreRecordService formScoreRecordService) {
        this.formScoreRecordService = formScoreRecordService;
    }

    @GetMapping("/")
    public ResponseEntity<List<FormScoreRecordEntity>> getAllFormScoreRecords() {
        return ResponseEntity.ok(formScoreRecordService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<FormScoreRecordEntity> getFormScoreRecordById(@PathVariable Long id) {
        return ResponseEntity.ok(formScoreRecordService.getById(id));
    }

    @PostMapping("/")
    public ResponseEntity<FormScoreRecordEntity> addFormScoreRecord(@RequestBody FormScoreRecordEntity newFormScoreRecord) {
        return ResponseEntity.ok(formScoreRecordService.create(newFormScoreRecord));
    }

    @PutMapping("/")
    public ResponseEntity<FormScoreRecordEntity> updateFormScoreRecordById(@RequestBody FormScoreRecordEntity newFormScoreRecord) {
        Long id = newFormScoreRecord.getId();
        return ResponseEntity.ok(formScoreRecordService.update(id, newFormScoreRecord));
    }

    @DeleteMapping("/")
    public ResponseEntity<Status> deleteFormScoreRecordById(@RequestBody Map<String, Object> payload) {
        String idString = (String) payload.get("id");
        Long id = Long.parseLong(idString);
        formScoreRecordService.delete(id);
        return ResponseEntity.ok(ResponseStatus.statusOk());
    }
}
