package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.entity.FormScoreRecordEntity;
import com.isslab.se_form_backend.entity.FormSubmissionEntity;
import com.isslab.se_form_backend.repository.FormScoreRecordRepository;
import com.isslab.se_form_backend.service.AbstractFormScoreRecordService;
import com.isslab.se_form_backend.service.AbstractFormSubmissionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FormScoreRecordService extends AbstractFormScoreRecordService {

    private final AbstractFormSubmissionService formSubmissionService;
    private final FormScoreRecordRepository repository;

    public FormScoreRecordService(FormScoreRecordRepository repository, AbstractFormSubmissionService formSubmissionService) {
        this.repository = repository;
        this.formSubmissionService = formSubmissionService;
    }

    public List<FormScoreRecordEntity> getAll() {
        return repository.findAll();
    }

    public FormScoreRecordEntity getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public FormScoreRecordEntity create(FormScoreRecordEntity record) {
        return repository.save(record);
    }

    public FormScoreRecordEntity update(Long id, FormScoreRecordEntity newRecord) {
        FormScoreRecordEntity record = repository.findById(id).orElse(null);

        if(record != null) {

            // 確認評分者是同一個人
            if(!Objects.equals(record.getReviewerId(), newRecord.getReviewerId())) return null;

            // 只需要改 score 就好，表單 id、評分者跟被評者都沿用就可以了
            record.setScore(newRecord.getScore());
            return repository.save(record);
        }

        return null;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<FormScoreRecordEntity> loadFormScoreRecordsByWeek(String week) {
        List<FormSubmissionEntity> formSubmissions = formSubmissionService.fetchAllSubmissionsByWeek(week);
        List<FormScoreRecordEntity> formScoreRecords = new ArrayList<>();

        for(FormSubmissionEntity formSubmission : formSubmissions) {
            Long formId = formSubmission.getId();
            FormScoreRecordEntity record = repository.findByFormId(formId);
            formScoreRecords.add(record);
        }

        return formScoreRecords;
    }
}
