package com.isslab.se_form_backend.service.impl.mock;

import com.isslab.se_form_backend.entity.FormScoreRecordEntity;
import com.isslab.se_form_backend.helper.MockFormScoreRecord;
import com.isslab.se_form_backend.service.AbstractFormScoreRecordService;

import java.util.List;

public class MockFormScoreRecordService extends AbstractFormScoreRecordService {
    @Override
    public List<FormScoreRecordEntity> loadFormScoreRecordsByWeek(String week) {
        return MockFormScoreRecord.mockRecord(week);
    }
}
