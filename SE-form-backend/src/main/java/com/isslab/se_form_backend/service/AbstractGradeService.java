package com.isslab.se_form_backend.service;

import com.isslab.se_form_backend.entity.FormScoreRecordEntity;
import com.isslab.se_form_backend.model.FormScoreRecord;
import com.isslab.se_form_backend.model.PresenterGradeSummary;
import com.isslab.se_form_backend.model.StudentGrade;
import com.isslab.se_form_backend.model.WeeklyGrade;
import com.isslab.se_form_backend.service.impl.GradeHelper;
import com.isslab.se_form_backend.service.impl.GradesToCSVService;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractGradeService {

    //zScore 的閾值
    private static final double zScoreThreshold = 2.5;

    //score 對照 grade
    private static final Map<String, Double> SCORE_MAP = Map.of(
            "A", 90.0,
            "B", 80.0,
            "C", 70.0
    );

    //TODO: 建立存取資料的方法

    private calculateGrade(FormScoreRecordEntity formScoreRecordEntity) {
        String presenterScore = formScoreRecordEntity.getScore();
        double presenterGrade = SCORE_MAP.getOrDefault(presenterScore, 0.0);

    }

}
