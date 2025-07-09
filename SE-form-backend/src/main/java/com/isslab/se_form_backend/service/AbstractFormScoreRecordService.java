package com.isslab.se_form_backend.service;

import com.isslab.se_form_backend.entity.FormScoreRecordEntity;
import com.isslab.se_form_backend.model.FormScoreRecord;
import com.isslab.se_form_backend.model.FormSubmission;

import java.util.List;

public abstract class AbstractFormScoreRecordService {

    public abstract List<FormScoreRecord> getAll();
    public abstract FormScoreRecord getById(Long id);
    public abstract void create(FormScoreRecord record);
    public abstract void update(Long id, FormScoreRecordEntity newRecord);
    public abstract void delete(Long id);
    public abstract void saveByFormSubmission(Long formId, FormSubmission formSubmission);
}
