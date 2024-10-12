package com.isslab.se_form_backend.service;

import com.isslab.se_form_backend.entity.FormEntity;
import com.isslab.se_form_backend.entity.ReviewEntity;
import com.isslab.se_form_backend.entity.ScoreEntity;
import com.isslab.se_form_backend.model.Grade;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class GradeService {

    private final GradesToCSVService gradesToCSVService;
    private final FormService formService;

    private static final Map<String, Integer> SCORE_MAP = Map.of(
            "A", 90,
            "B", 80,
            "C", 70
    );

    public GradeService(GradesToCSVService gradesToCSVService, FormService formService) {
        this.gradesToCSVService = gradesToCSVService;
        this.formService = formService;
    }

    public void getAllUndergraduatesGrades(String week) {
        Grade grade1 = Grade.builder().studentId("113000001").grade(86.41).build();
        Grade grade2 = Grade.builder().studentId("113000002").grade(93.22).build();
        Grade grade3 = Grade.builder().studentId("113000003").grade(93.22).build();
        Grade grade4 = Grade.builder().studentId("113000004").grade(75).build();
        Grade grade5 = Grade.builder().studentId("113000005").grade(60).build();

        List<Grade> grades = List.of(grade1, grade2, grade3, grade4, grade5);
        gradesToCSVService.createGradeCSV(grades, "undergraduates", week);
    }

    public void getAllOnServiceGrades(String week) {
        Grade grade1 = Grade.builder().studentId("113500001").grade(80).build();
        Grade grade2 = Grade.builder().studentId("113500002").grade(78.41).build();
        Grade grade3 = Grade.builder().studentId("113500003").grade(88.5).build();

        List<Grade> grades = List.of(grade1, grade2, grade3);
        gradesToCSVService.createGradeCSV(grades, "onService", week);
    }

    public List<ScoreEntity> createGradeTable() {
        List<ReviewEntity> reviews = formService.getFormReviewByFormId(1L);
        List<ScoreEntity> scoreList = createScoreList(reviews);
        return calculateGrades(scoreList);
    }

    private List<ScoreEntity> createScoreList(List<ReviewEntity> reviews) {
        Map<Long, FormEntity> formCache = new HashMap<>();
        List<ScoreEntity> scoreEntities = new ArrayList<>();

        for (ReviewEntity review : reviews) {
            Long formId = review.getFormId();
            FormEntity form = formCache.computeIfAbsent(formId, formService::getFormById);

            Date reviewDate = form.getReviewDate();
            String presenterId = review.getPresenterId();
            String reviewerId = review.getReviewerId();
            String score = review.getScore();
            int grade = SCORE_MAP.getOrDefault(score, 0);

            ScoreEntity scoreEntity = ScoreEntity.builder().reviewDate(reviewDate).presenterId(presenterId).reviewerId(reviewerId).score(score).grade(grade).build();
            scoreEntities.add(scoreEntity);
        }
        return scoreEntities;
    }

    private DescriptiveStatistics setStatistics(List<ScoreEntity> scoreList) {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (ScoreEntity scoreDetail : scoreList) {
            stats.addValue(scoreDetail.getGrade());
        }
        return stats;
    }

    private void calculateReviewersGrades(ScoreEntity scoreDetail, double standardDeviation) {
        int grade = scoreDetail.getGrade();
        double presenterScore = scoreDetail.getPresenterGrade();
        double zScore = (grade - presenterScore) / standardDeviation;
        double reviewerGrade = 100 - (Math.abs(zScore) / 3) * 20;

        scoreDetail.setZScore(zScore);
        scoreDetail.setReviewerGrade(reviewerGrade);
        scoreDetail.setOutlier(Math.abs(zScore) > 2.5);
        scoreDetail.setRound(1);
    }

    // 將 ScoreEntity 依照 presenterId 分組並計算成績
    private List<ScoreEntity> calculateGrades(List<ScoreEntity> scoreList) {
        // 使用 Map 根據 presenterId 進行分組
        Map<String, List<ScoreEntity>> scoresByPresenter = scoreList.stream()
                .collect(Collectors.groupingBy(ScoreEntity::getPresenterId));

        // 遍歷每個 presenter 的分數，分別計算 Z-Score 和其他統計數據
        for (Map.Entry<String, List<ScoreEntity>> entry : scoresByPresenter.entrySet()) {
            List<ScoreEntity> scoreListByPresenter = entry.getValue();

            // 計算該報告者的的統計數據
            DescriptiveStatistics stats = setStatistics(scoreListByPresenter);
            double presenterScore = stats.getMean();
            double standardDeviation = stats.getStandardDeviation();

            // 避免除以 0 的情況
            if (standardDeviation == 0) {
                standardDeviation = 1;
            }

            // 計算每位 reviewer 的成績
            for (ScoreEntity scoreDetail : scoreListByPresenter) {
                scoreDetail.setPresenterGrade(presenterScore); // 設定 presenter 的平均分數
                calculateReviewersGrades(scoreDetail, standardDeviation); // 計算 Z-Score 等數據
            }
        }

        return scoreList;
    }

}
