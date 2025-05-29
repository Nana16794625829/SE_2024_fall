package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.entity.FormScoreRecordEntity;
import com.isslab.se_form_backend.service.AbstractGradeService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
public class MockGradeService extends AbstractGradeService {

    private List<FormScoreRecordEntity> mockData;

    public MockGradeService(List<FormScoreRecordEntity> mockData) {
        this.mockData = mockData;
    }

    @Override
    protected List<FormScoreRecordEntity> loadFormScoreRecords() {
        return mockData;
    }
}