package com.isslab.se_form_backend.service;

import com.isslab.se_form_backend.entity.FormEntity;
import com.isslab.se_form_backend.entity.ReviewEntity;
import com.isslab.se_form_backend.entity.GradeEntity;
import com.isslab.se_form_backend.model.Grade;
import com.isslab.se_form_backend.repository.GradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class GradeService {

    private final GradesToCSVService gradesToCSVService;
    private final FormService formService;
    private final GradeRepository gradeRepository;

    private static final Map<String, Integer> SCORE_MAP = Map.of(
            "A", 90,
            "B", 80,
            "C", 70
    );

    public GradeService(GradesToCSVService gradesToCSVService,
                        FormService formService,
                        GradeRepository gradeRepository) {
        this.gradesToCSVService = gradesToCSVService;
        this.formService = formService;
        this.gradeRepository = gradeRepository;
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

    public List<GradeEntity> createGradeTable() {
        List<ReviewEntity> reviews = formService.getFormReviewByFormId(1L);
        List<GradeEntity> gradeList = createGradeList(reviews);
        return calculateGrades(gradeList);
    }

    private List<GradeEntity> createGradeList(List<ReviewEntity> reviews) {
        Map<Long, FormEntity> formCache = new HashMap<>();
        List<GradeEntity> gradeEntities = new ArrayList<>();

        for (ReviewEntity review : reviews) {
            Long formId = review.getFormId();
            FormEntity form = formCache.computeIfAbsent(formId, formService::getFormById);

            Date reviewDate = form.getReviewDate();
            String presenterId = review.getPresenterId();
            String reviewerId = review.getReviewerId();
            String score = review.getScore();
            int grade = SCORE_MAP.getOrDefault(score, 0);

            GradeEntity gradeEntity = new GradeEntity(null, reviewDate, presenterId, 1, reviewerId, score, grade, 0, 0, 0, 0 , false, 0);
            gradeRepository.save(gradeEntity);
            gradeEntities.add(gradeEntity);
        }
        return gradeEntities;
    }

    private DescriptiveStatistics setStatistics(List<GradeEntity> gradeList) {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (GradeEntity gradeDetail : gradeList) {
            stats.addValue(gradeDetail.getGrade());
        }
        return stats;
    }

    protected void calculateReviewersGrades(GradeEntity gradeDetail, double standardDeviation) {
        int grade = gradeDetail.getGrade();
        double presenterGrade = gradeDetail.getPresenterGrade();
        double zScore = (grade - presenterGrade) / standardDeviation;

        int gradeGap = 20;
        double zScoreThreshold = 2.5;
        double reviewerGrade = 100 - (Math.abs(zScore) / 3) * gradeGap;
        Boolean outlier = Math.abs(zScore) > zScoreThreshold;

        String presenterId = gradeDetail.getPresenterId();
        String reviewerId = gradeDetail.getReviewerId();

        gradeDetail.setZScore(zScore);
        gradeDetail.setReviewerGrade(reviewerGrade);
        gradeDetail.setOutlier(Math.abs(zScore) > zScoreThreshold);
        gradeDetail.setRound(1);

        gradeRepository.updateReviewerDetailByReviewerIdAndPresenterId(reviewerId, presenterId, standardDeviation, zScore, reviewerGrade, outlier, 1);
    }

    // 將 GradeEntity 依照 presenterId 分組並計算成績
    protected List<GradeEntity> calculateGrades(List<GradeEntity> gradeList) {
        // 使用 Map 根據 presenterId 進行分組
        Map<String, List<GradeEntity>> gradesByPresenter = gradeList.stream()
                .collect(Collectors.groupingBy(GradeEntity::getPresenterId));

        // 遍歷每個 presenter 的分數，分別計算所有計算成績需要的統計數據
        for (Map.Entry<String, List<GradeEntity>> entry : gradesByPresenter.entrySet()) {
            List<GradeEntity> gradeListByPresenter = entry.getValue();

            // 計算該報告者的標準差與平均
            DescriptiveStatistics stats = setStatistics(gradeListByPresenter);
            double presenterGrade = stats.getMean();
            double standardDeviation = stats.getStandardDeviation();

            // 避免除以 0 的情況
            if (standardDeviation == 0) {
                standardDeviation = 1;
            }

            // 計算每位 reviewer 的成績
            for (GradeEntity gradeDetail : gradeListByPresenter) {
                String presenterId = gradeDetail.getPresenterId();
                gradeRepository.updatePresenterGradeByPresenterId(presenterGrade, presenterId);
                gradeDetail.setPresenterGrade(presenterGrade); // 設定 presenter 的平均分數
                gradeDetail.setStandardDeviation(standardDeviation);
                calculateReviewersGrades(gradeDetail, standardDeviation); // 計算 Z-Score 等數據
            }
        }

        return gradeList;
    }

}
