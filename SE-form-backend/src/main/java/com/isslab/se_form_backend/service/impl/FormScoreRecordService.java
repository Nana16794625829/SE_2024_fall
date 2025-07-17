package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.entity.FormScoreRecordEntity;
import com.isslab.se_form_backend.model.FormScoreRecord;
import com.isslab.se_form_backend.model.FormSubmission;
import com.isslab.se_form_backend.repository.FormScoreRecordRepository;
import com.isslab.se_form_backend.service.AbstractFormScoreRecordService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FormScoreRecordService extends AbstractFormScoreRecordService {

    private final FormScoreRecordRepository repository;

    public FormScoreRecordService(FormScoreRecordRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<FormScoreRecord> getAll() {
        List<FormScoreRecordEntity> entities = repository.findAll();
        List<FormScoreRecord> records = new ArrayList<>();

        for(FormScoreRecordEntity entity : entities) {
            FormScoreRecord record = new FormScoreRecord(
                    entity.getScore(),
                    entity.getPresenterId()
            );

            records.add(record);
        }

        return records;
    }

    @Override
    public FormScoreRecord getById(Long id) {
        FormScoreRecordEntity entity = repository.findById(id).orElse(null);
        if(entity == null) return null;

        return new FormScoreRecord(
                entity.getScore(),
                entity.getPresenterId()
        );
    }

    @Override
    public void save(FormScoreRecordEntity record) {
        repository.save(record);
    }

    @Override
    public void saveAll(List<FormScoreRecordEntity> records) {
        repository.saveAll(records);
    }

    @Override
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

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public FormScoreRecordEntity findByFormIdAndPresenterId(Long formId, String presenterId) {
        return repository.findByFormIdAndPresenterId(formId, presenterId);
    }
}
