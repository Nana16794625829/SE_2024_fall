package com.isslab.se_form_backend.service.impl.mock;

import com.isslab.se_form_backend.entity.FormScoreRecordEntity;
import com.isslab.se_form_backend.helper.MockDataBuilder;
import com.isslab.se_form_backend.model.FormScoreRecord;
import com.isslab.se_form_backend.model.FormSubmission;
import com.isslab.se_form_backend.service.AbstractFormScoreRecordService;

import java.util.List;

public class MockFormScoreRecordService extends AbstractFormScoreRecordService {
    @Override
    public List<FormScoreRecord> getAll() {
        return List.of();
    }

    @Override
    public FormScoreRecord getById(Long id) {
        return null;
    }

    @Override
    public void save(FormScoreRecord record) {
    }

    @Override
    public void update(Long id, FormScoreRecordEntity newRecord) {
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public FormScoreRecordEntity findByFormIdAndPresenterId(Long formId, String presenterId) {
        return null;
    }

//    @Override
//    public void saveByFormSubmission(Long formId, FormSubmission formSubmission) {
//
//    }
}
