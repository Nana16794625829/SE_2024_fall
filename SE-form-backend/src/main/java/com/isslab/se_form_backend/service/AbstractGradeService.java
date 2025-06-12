package com.isslab.se_form_backend.service;

import com.isslab.se_form_backend.entity.FormScoreRecordEntity;
import com.isslab.se_form_backend.service.impl.GradeHelper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractGradeService {

    //zScore 的閾值
    private static final double zScoreThreshold = 2.5;
    private static final double outlierGrade = 60.0;

    //score 對照 grade
    private static final Map<String, Double> SCORE_MAP = Map.of(
            "A", 90.0,
            "B", 80.0,
            "C", 70.0
    );

    private final Map<String, Double> presenterGradeMap = new HashMap<>();
    private final Map<String, Double> reviewerGradeMap = new HashMap<>();
    private final Map<String, Double> reviewerZScoreMap = new HashMap<>();
    private double presenterAvgGrade;
    private double stdDev;

    public Map<String, Double> calculateGrade() {
        List<FormScoreRecordEntity> records = loadFormScoreRecords();
        return calculateGrade(records);
    }

    public Map<String, Double> calculateGrade(List<FormScoreRecordEntity> formScoreRecordList) {

        // map the reviewers' id and the scores for the presenter.
        setPresenterGradeMap(formScoreRecordList);

        // calculate the mean (presenter's grade) and stdDev with grades.
        setPresenterGradeStatics();

        // Map the studentId to their zScore.
        setReviewerZScoreMap();

        // calculate the reviewers' grade for first time.
        assignReviewerGradesByRound(1);

        // remove the outliers.
        removeOutliersForRound2();

        // re-calculate the statics for round 2.
        setPresenterGradeStatics();
        setReviewerZScoreMap();

        // calculate the reviewers' grade for second time (without outliers.)
        assignReviewerGradesByRound(2);

        return reviewerGradeMap;
    }

    public abstract void saveGradeToStudent(String studentId, String week, double grade);
    public abstract double getGradeByIdAndWeek(String studentId, String week);

    protected abstract List<FormScoreRecordEntity> loadFormScoreRecords();

    protected double calculateZScore(Map.Entry<String, Double> entry) {
        double gradeByReviewer = entry.getValue();
        return (gradeByReviewer - presenterAvgGrade) / stdDev;
    }

    protected void setPresenterGradeMap(List<FormScoreRecordEntity> formScoreRecordList) {
        for(FormScoreRecordEntity formScoreRecord : formScoreRecordList) {
            String presenterScore = formScoreRecord.getScore();
            String reviewerId = formScoreRecord.getReviewerId();

            double presenterGrade = SCORE_MAP.getOrDefault(presenterScore, 0.0);
            presenterGradeMap.put(reviewerId, presenterGrade);
        }
    }

    protected void setPresenterGradeStatics() {
        DescriptiveStatistics presenterGradeStatics = new DescriptiveStatistics();
        for (double grade : presenterGradeMap.values()) {
            presenterGradeStatics.addValue(grade);
        }
        presenterAvgGrade = presenterGradeStatics.getMean();
        stdDev = GradeHelper.calculatePopulationStandardDeviation(presenterGradeStatics);
        if (stdDev == 0) stdDev = 1; // 避免除以 0 的狀況
    }

    protected void setReviewerZScoreMap() {
        for(Map.Entry<String, Double> entry : presenterGradeMap.entrySet()) {
            String studentId = entry.getKey();
            double zScore = calculateZScore(entry);
            reviewerZScoreMap.put(studentId, zScore);
        }
    }

    protected double calculateReviewerGrade(double zScore) {
        double scoreGap = GradeHelper.getGradeGap(SCORE_MAP);
        double reviewerGrade = 100 - (Math.abs(zScore) / 3) * scoreGap;

        // 四捨五入兩次避免精度問題
        BigDecimal bd = new BigDecimal(Double.toString(reviewerGrade))
                .setScale(2, RoundingMode.HALF_UP)
                .setScale(1, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    protected void assignReviewerGradesByRound(int round) {
        for (Map.Entry<String, Double> entry : reviewerZScoreMap.entrySet()) {
            String studentId = entry.getKey();
            double zScore = entry.getValue();

            // Round 1: identify the outliers and calculate the grades for other reviewers.
            // Round 2: calculate the grades for reviewers except for the outliers from round 1.
            if (round < 2 && Math.abs(zScore) > zScoreThreshold) {
                reviewerGradeMap.put(studentId, outlierGrade);
            } else {
                double reviewerGrade = calculateReviewerGrade(zScore);
                reviewerGradeMap.put(studentId, reviewerGrade);
            }
        }
    }

    protected void removeOutliersForRound2() {
        // 收集要被移除的 students
        Set<String> keysToRemove = new HashSet<>();
        for (Map.Entry<String, Double> entry : reviewerGradeMap.entrySet()) {
            if (entry.getValue() <= outlierGrade) {
                keysToRemove.add(entry.getKey());
            }
        }

        // 移除第二輪計算時會使用到的 map 中的 outliers
        for (String key : keysToRemove) {
            presenterGradeMap.remove(key);
            reviewerZScoreMap.remove(key);
        }
    }
}
