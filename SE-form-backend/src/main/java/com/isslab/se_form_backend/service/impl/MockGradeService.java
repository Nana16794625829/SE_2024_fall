package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.entity.FormScoreRecordEntity;
import com.isslab.se_form_backend.helper.GradeMockDataLoader;
import com.isslab.se_form_backend.service.AbstractGradeService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


@Slf4j
public class MockGradeService extends AbstractGradeService {

    public MockGradeService() {}

    @Override
    protected List<FormScoreRecordEntity> loadFormScoreRecords() {
        return GradeMockDataLoader.loadFromCsv("/mock/sample.csv");
    }
}