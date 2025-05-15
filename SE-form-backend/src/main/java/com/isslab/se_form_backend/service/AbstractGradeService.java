package com.isslab.se_form_backend.service;

import com.isslab.se_form_backend.entity.FormScoreRecordEntity;
import com.isslab.se_form_backend.service.impl.GradeHelper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

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

    private final Map<String, Double> reviewerGradeMap = new HashMap<>();
    private final Map<String, Double> reviewerZScoreMap = new HashMap<>();
    private double presenterAvgGrade;
    private double stdDev;

    //TODO: 建立存取資料的方法

    public Map<String, Double> calculateGrade(List<FormScoreRecordEntity> formScoreRecordList) {

        // map the reviewers' id and the scores for the presenter.
        setReviewerGradeMap(formScoreRecordList);

        // calculate the mean (presenter's grade) and stdDev with grades.
        setPresenterGradeStatics();

        // Map the studentId to their zScore.
        setReviewerZScoreMap(reviewerGradeMap);

        // calculate the reviewers' grade for first time.
        assignReviewerGradesByRound(1);

        // remove the outliers.
        Map<String, Double> reviewerGradeMapClone = removeOutliersForRound2();

        // re-calculate the zScores for round 2.
        setReviewerZScoreMap(reviewerGradeMapClone);

        // calculate the reviewers' grade for second time (without outliers.)
        assignReviewerGradesByRound(2);

        // record the final grades for all reviewers and outliers.
        setFinalGradeForReviewers(reviewerGradeMapClone);

        return reviewerGradeMap;
    }

    private double calculateZScore(Map.Entry<String, Double> entry) {
        double gradeByReviewer = entry.getValue();
        return (gradeByReviewer - presenterAvgGrade) / stdDev;
    }

    private double calculateReviewerGrade(double zScore) {
        double scoreGap = GradeHelper.getGradeGap(SCORE_MAP);
        return 100 - (Math.abs(zScore) / 3) * scoreGap;
    }

    private void assignReviewerGradesByRound(int round) {
        for (Map.Entry<String, Double> entry : reviewerZScoreMap.entrySet()) {
            String studentId = entry.getKey();
            double zScore = entry.getValue();

            // Round 1: identify the outliers and calculate the grades for other reviewers.
            // Round 2: calculate the grades for reviewers except for the outliers from round 1.
            if (round < 2 && zScore > zScoreThreshold) {
                reviewerGradeMap.put(studentId, outlierGrade);
            }else {
                double reviewerGrade = calculateReviewerGrade(zScore);
                reviewerGradeMap.put(studentId, reviewerGrade);
            }
        }
    }

    private Map<String, Double> removeOutliersForRound2() {
        Map<String, Double> reviewerGradeMapClone = new HashMap<>(reviewerGradeMap);

        // 收集要被移除的 students
        Set<String> keysToRemove = new HashSet<>();
        for (Map.Entry<String, Double> entry : reviewerGradeMapClone.entrySet()) {
            if (entry.getValue() <= outlierGrade) {
                keysToRemove.add(entry.getKey());
            }
        }

        // 從兩個 Map 中移除這些鍵
        for (String key : keysToRemove) {
            reviewerGradeMapClone.remove(key);
            reviewerZScoreMap.remove(key); // 同時從 Z-Score Map 中移除
        }

        return reviewerGradeMapClone;
    }

    private void setFinalGradeForReviewers(Map<String, Double> reviewerGradeMapClone) {
        for(Map.Entry<String, Double> entry : reviewerGradeMapClone.entrySet()) {
            reviewerGradeMap.replace(entry.getKey(), entry.getValue());
        }
    }

    private void setReviewerGradeMap(List<FormScoreRecordEntity> formScoreRecordList) {
        for(FormScoreRecordEntity formScoreRecord : formScoreRecordList) {
            String presenterScore = formScoreRecord.getScore();
            String reviewerId = formScoreRecord.getReviewerId();

            double presenterGrade = SCORE_MAP.getOrDefault(presenterScore, 0.0);
            reviewerGradeMap.put(reviewerId, presenterGrade);
        }
    }

    private void setPresenterGradeStatics() {
        DescriptiveStatistics presenterGradeStatics = new DescriptiveStatistics();
        for (double grade : reviewerGradeMap.values()) {
            presenterGradeStatics.addValue(grade);
        }
        presenterAvgGrade = presenterGradeStatics.getMean();
        stdDev = GradeHelper.calculatePopulationStandardDeviation(presenterGradeStatics);
    }

    private void setReviewerZScoreMap(Map<String, Double> reviewerGradeMap) {
        for(Map.Entry<String, Double> entry : reviewerGradeMap.entrySet()) {
            String studentId = entry.getKey();
            double zScore = calculateZScore(entry);
            reviewerZScoreMap.put(studentId, zScore);
        }
    }
}
