package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.entity.FormScoreRecordEntity;
import com.isslab.se_form_backend.service.AbstractGradeService;

import java.util.List;

public class GradeService extends AbstractGradeService {
    @Override
    protected List<FormScoreRecordEntity> loadFormScoreRecords() {
        return List.of();
    }
}
