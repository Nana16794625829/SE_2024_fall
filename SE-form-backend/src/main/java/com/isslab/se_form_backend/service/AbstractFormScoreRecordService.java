package com.isslab.se_form_backend.service;

import com.isslab.se_form_backend.entity.FormScoreRecordEntity;

import java.util.List;

public abstract class AbstractFormScoreRecordService {

    public abstract List<FormScoreRecordEntity> loadFormScoreRecordsByWeek(String week);
}
