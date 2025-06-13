package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.entity.FormScoreRecordEntity;
import com.isslab.se_form_backend.service.AbstractGradeService;

import java.util.List;
import java.util.Map;

public class GradeService extends AbstractGradeService {
    @Override
    public void saveGradeToStudent(String studentId, String week, double grade) {

    }

    @Override
    public double getGradeByIdAndWeek(String studentId, String week) {
        return 0;
    }

    @Override
    public void updateGradeByIdAndWeek(String studentId, String week, double grade) {

    }

    @Override
    protected List<FormScoreRecordEntity> loadFormScoreRecords() {
        return List.of();
    }
}
