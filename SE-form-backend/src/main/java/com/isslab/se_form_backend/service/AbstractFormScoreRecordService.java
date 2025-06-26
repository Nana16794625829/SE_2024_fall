package com.isslab.se_form_backend.service;

import com.isslab.se_form_backend.entity.FormScoreRecordEntity;

import java.util.List;

public abstract class AbstractFormScoreRecordService {

    public abstract List<FormScoreRecordEntity> loadFormScoreRecordsByWeek(String week);
    public abstract List<FormScoreRecordEntity> getAll();
    public abstract FormScoreRecordEntity getById(Long id);
    public abstract FormScoreRecordEntity create(FormScoreRecordEntity record);
    public abstract FormScoreRecordEntity update(Long id, FormScoreRecordEntity newRecord);
    public abstract void delete(Long id);
}
