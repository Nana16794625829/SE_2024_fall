package com.isslab.se_form_backend.service.impl.mock;

import com.isslab.se_form_backend.entity.FormScoreRecordEntity;
import com.isslab.se_form_backend.helper.MockDataBuilder;
import com.isslab.se_form_backend.service.AbstractFormScoreRecordService;

import java.util.List;

public class MockFormScoreRecordService extends AbstractFormScoreRecordService {
    @Override
    public List<FormScoreRecordEntity> loadFormScoreRecordsByWeekAndPresenter(String week, String presenterId) {
        return MockDataBuilder.mockRecord(week);
    }

    @Override
    public List<FormScoreRecordEntity> getAll() {
        return List.of();
    }

    @Override
    public FormScoreRecordEntity getById(Long id) {
        return null;
    }

    @Override
    public FormScoreRecordEntity create(FormScoreRecordEntity record) {
        return null;
    }

    @Override
    public FormScoreRecordEntity update(Long id, FormScoreRecordEntity newRecord) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
