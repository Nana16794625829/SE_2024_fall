package com.isslab.se_form_backend.service;

import com.isslab.se_form_backend.entity.FormEntity;
import com.isslab.se_form_backend.entity.GradeEntity;
import com.isslab.se_form_backend.entity.ReviewEntity;
import com.isslab.se_form_backend.model.Grade;
import com.isslab.se_form_backend.model.Statistic;
import com.isslab.se_form_backend.service.impl.GradeHelper;
import com.isslab.se_form_backend.service.impl.GradesToCSVService;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractGradeService {
    private final GradesToCSVService gradesToCSVService;
    private final IFormService formService;

    //zScore 的閾值
    private static final double zScoreThreshold = 2.5;

    //score 對照 grade
    private static final Map<String, Integer> SCORE_MAP = Map.of(
            "A", 90,
            "B", 80,
            "C", 70
    );

    public AbstractGradeService(GradesToCSVService gradesToCSVService,
                        IFormService formService) {
        this.gradesToCSVService = gradesToCSVService;
        this.formService = formService;
    }

    //拿出日間部同學的資料
    protected abstract List<Grade> getUndergraduatesGradesByWeek(String week);

    public void exportAllUndergraduatesGrades(String week) {
        List<Grade> grades = getUndergraduatesGradesByWeek(week);
        gradesToCSVService.createGradeCSV(grades, "undergraduates", week);
    }

    //拿出在職班同學的資料
    protected abstract List<Grade> getOnServiceGradesByWeek(String week);

    public void exportAllOnServiceGrades(String week) {
        List<Grade> grades = getOnServiceGradesByWeek(week);
        gradesToCSVService.createGradeCSV(grades, "onService", week);
    }

    public List<GradeEntity> createGradeTable() {
        List<ReviewEntity> reviews = formService.getFormReviewByFormId(1L);
        List<GradeEntity> tmpGradeList = calculateGradesRoundOne(reviews);
//        List<GradeEntity> finalGradeList = calculateGradesRoundTwo(tmpGradeList);

        return calculateGrades(tmpGradeList);
    }

    protected abstract void save(GradeEntity gradeEntity);

    private List<GradeEntity> mapScoresToGradesFromReviews(List<ReviewEntity> reviews) {
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

            GradeEntity gradeEntity = GradeEntity
                    .builder()
                    .reviewDate(reviewDate)
                    .presenterId(presenterId)
                    .presentOrder(1)
                    .reviewerId(reviewerId)
                    .score(score)
                    .grade(grade)
                    .build();

            save(gradeEntity);
            gradeEntities.add(gradeEntity);
        }
        return gradeEntities;
    }

    private List<GradeEntity> calculateGradesRoundOne(List<ReviewEntity> reviews) {
        List<GradeEntity> gradeEntities = mapScoresToGradesFromReviews(reviews);

        return gradeEntities;
    }

    //  為了提供 test 測試，所以先設定為 public
    public List<GradeEntity> calculateGrades(List<GradeEntity> gradeList) {
        List<GradeEntity> result = new ArrayList<>();

        // 使用 Map 根據 presenterId 進行分組
        Map<String, List<GradeEntity>> gradesByPresenter = gradeList.stream()
                .collect(Collectors.groupingBy(GradeEntity::getPresenterId));

        // 遍歷每個 presenter 的分數，分別計算所有計算成績需要的統計數據
        for (Map.Entry<String, List<GradeEntity>> entry : gradesByPresenter.entrySet()) {
            List<GradeEntity> gradeListByPresenter = entry.getValue();

            // 計算該報告者的標準差與平均
            double presenterGrade = calculateMean(gradeListByPresenter);
            double standardDeviation = calculateStandardDeviation(gradeListByPresenter);

            // 避免除以 0 的情況
            if (standardDeviation == 0) {
                standardDeviation = 1;
            }

            // 計算每位 reviewer 的成績
            calculateReviewersGrades(gradeListByPresenter, presenterGrade, standardDeviation);

            result.addAll(gradeListByPresenter);
        }

        result.sort(Comparator.comparingLong(GradeEntity::getId));
        return result;
    }

    private double calculateMean(List<GradeEntity> gradeList) {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (GradeEntity grade : gradeList) {
            stats.addValue(grade.getGrade());
        }
        return stats.getMean();
    }

    private double calculateStandardDeviation(List<GradeEntity> gradeList) {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (GradeEntity grade : gradeList) {
            stats.addValue(grade.getGrade());
        }
        return stats.getStandardDeviation();
    }

    private void calculateReviewersGrades(List<GradeEntity> gradeListByPresenter, double presenterGrade, double standardDeviation) {
        for (GradeEntity gradeDetail : gradeListByPresenter) {
            String presenterId = gradeDetail.getPresenterId();
            updatePresenterGradeByPresenterId(presenterGrade, presenterId);
            gradeDetail.setPresenterGrade(presenterGrade); // 設定 presenter 的平均分數
            gradeDetail.setStandardDeviation(standardDeviation);

            //  計算 Z-Score, reviewer's grade 以及判定是否為 outlier
            Statistic statistic = calculateReviewersStatistics(gradeDetail);

            gradeDetail.setZScore(statistic.getZScore());
            gradeDetail.setGrade(statistic.getGradeGap());
            gradeDetail.setReviewerGrade(statistic.getReviewerGrade());
            gradeDetail.setOutlier(statistic.getOutlier());
        }
    }

    private Statistic calculateReviewersStatistics(GradeEntity gradeDetail) {
        double presenterGrade = gradeDetail.getPresenterGrade();
        double standardDeviation = gradeDetail.getStandardDeviation();
        int gradeByScore = gradeDetail.getGrade();

        double zScore = GradeHelper.calculateZScore(gradeByScore, presenterGrade, standardDeviation);
        int gradeGap = GradeHelper.getGradeGap(SCORE_MAP);
        double reviewerGrade = GradeHelper.calculateReviewerGrade(zScore, gradeGap);
        boolean outlier = GradeHelper.isOutlier(zScore, 2.5);

        return Statistic.builder()
                .zScore(zScore)
                .gradeGap(gradeGap)
                .reviewerGrade(reviewerGrade)
                .outlier(outlier)
                .build();
    }

    /*
     * TODO:
     *  1. 忘記這個用在哪了
     *  2. 把 grade calculate round 2 完成
     */
    private DescriptiveStatistics setStatistics(List<GradeEntity> gradeList) {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (GradeEntity gradeDetail : gradeList) {
            stats.addValue(gradeDetail.getGrade());
        }
        return stats;
    }

    protected abstract void updateReviewerDetailByReviewerIdAndPresenterId(
           String reviewerId,
           String presenterId,
           double standardDeviation,
           double zScore,
           double reviewerGrade,
           Boolean outlier,
           int round
    );

    protected abstract void updatePresenterGradeByPresenterId(double presenterGrade, String presenterId);
}
