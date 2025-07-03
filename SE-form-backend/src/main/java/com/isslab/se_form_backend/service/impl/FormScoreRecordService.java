package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.entity.FormScoreRecordEntity;
import com.isslab.se_form_backend.entity.FormSubmissionEntity;
import com.isslab.se_form_backend.model.FormScoreRecord;
import com.isslab.se_form_backend.repository.FormScoreRecordRepository;
import com.isslab.se_form_backend.service.AbstractFormScoreRecordService;
import com.isslab.se_form_backend.service.AbstractFormSubmissionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FormScoreRecordService extends AbstractFormScoreRecordService {

    private final AbstractFormSubmissionService formSubmissionService;
    private final FormScoreRecordRepository repository;

    public FormScoreRecordService(FormScoreRecordRepository repository, AbstractFormSubmissionService formSubmissionService) {
        this.repository = repository;
        this.formSubmissionService = formSubmissionService;
    }

    public List<FormScoreRecord> getAll() {
        List<FormScoreRecordEntity> entities = repository.findAll();
        List<FormScoreRecord> records = new ArrayList<>();

        for(FormScoreRecordEntity entity : entities) {
            FormScoreRecord record = new FormScoreRecord(
                    entity.getFormId(),
                    entity.getScore(),
                    entity.getReviewerId(),
                    entity.getPresenterId()
            );

            records.add(record);
        }

        return records;
    }

    public FormScoreRecord getById(Long id) {
        FormScoreRecordEntity entity = repository.findById(id).orElse(null);
        if(entity == null) return null;

        return new FormScoreRecord(
                entity.getFormId(),
                entity.getScore(),
                entity.getReviewerId(),
                entity.getPresenterId()
        );
    }

    public void create(FormScoreRecord record) {
        FormScoreRecordEntity entity = FormScoreRecordEntity
                .builder()
                .formId(record.getFormId())
                .score(record.getScore())
                .reviewerId(record.getReviewerId())
                .presenterId(record.getPresenterId())
                .build();


        repository.save(entity);
    }

    public void update(Long id, FormScoreRecordEntity newRecord) {
        FormScoreRecordEntity record = repository.findById(id).orElse(null);

        if(record != null) {

            // 確認評分者是同一個人
            if(!Objects.equals(record.getReviewerId(), newRecord.getReviewerId())){
                throw new IllegalArgumentException("Reviewer 必須相同\n" + "Recorded reviewer: " + record.getReviewerId() + "\n" + "New reviewer: " + newRecord.getReviewerId());
            };

            // 只需要改 score 就好，表單 id、評分者跟被評者都沿用就可以了
            record.setScore(newRecord.getScore());
            repository.save(record);
        }
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<FormScoreRecordEntity> loadFormScoreRecordsByWeekAndPresenter(String week, String presenterId) {
        List<FormSubmissionEntity> formSubmissions = formSubmissionService.fetchAllSubmissionsByWeek(week);
        List<FormScoreRecordEntity> formScoreRecords = new ArrayList<>();

        for(FormSubmissionEntity formSubmission : formSubmissions) {
            Long formId = formSubmission.getId();
            FormScoreRecordEntity record = repository.findByFormIdAndPresenterId(formId, presenterId);
            formScoreRecords.add(record);
        }

        return formScoreRecords;
    }
}
