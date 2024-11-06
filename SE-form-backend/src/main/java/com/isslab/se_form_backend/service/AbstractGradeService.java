package com.isslab.se_form_backend.service;

import com.isslab.se_form_backend.entity.FormEntity;
import com.isslab.se_form_backend.entity.GradeEntity;
import com.isslab.se_form_backend.entity.ReviewEntity;
import com.isslab.se_form_backend.model.Grade;
import com.isslab.se_form_backend.model.Statistic;
import com.isslab.se_form_backend.model.StudentGrade;
import com.isslab.se_form_backend.model.WeeklyGrade;
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

    //存入日間部同學的每週成績
    protected abstract void addUndergraduatesWeeklyGrades(String week, List<StudentGrade> studentGrades);

    //存入在職班同學的每週成績
    protected abstract void addOnServiceWeeklyGrades(String week, StudentGrade studentGrades);

    //以週次拿出同學的成績（不分班都可以使用）
    protected abstract WeeklyGrade getGradesByWeek(String week);

    public void exportAllUndergraduatesGrades(String week) {
        WeeklyGrade grades = getGradesByWeek(week);
        // TODO: 輸出當周多個成績的CSV //
//        gradesToCSVService.createGradeCSV(grades, "undergraduates", week);
    }

    public void exportAllOnServiceGrades(String week) {
        WeeklyGrade grades = getGradesByWeek(week);
        // TODO: 輸出當周成績的CSV //
//        gradesToCSVService.createGradeCSV(grades, "onService", week);
    }

    public void createGradeTable() {
        List<ReviewEntity> reviews = formService.getFormReviews();
        List<GradeEntity> tmpGradeList = calculateGradesRoundOne(reviews);
        List<GradeEntity> finalGradeList = calculateGradesRoundTwo(tmpGradeList);

        for(GradeEntity grade : finalGradeList) save(grade);
    }

    private List<GradeEntity> calculateGradesRoundOne(List<ReviewEntity> reviews) {
        List<GradeEntity> gradeList = mapScoresToGradesFromReviews(reviews);
        gradeList.sort(Comparator.comparingLong(GradeEntity::getId));
        return listGradesByPresenter(gradeList);
    }

    private List<GradeEntity> calculateGradesRoundTwo(List<GradeEntity> tmpGradeList) {
        List<GradeEntity> outlierList = new ArrayList<>();
        List<GradeEntity> nonOutlierList = new ArrayList<>();

        for(GradeEntity tmpGrade : tmpGradeList) {
            tmpGrade.setRound(tmpGrade.getRound() + 1);

            if(tmpGrade.isOutlier()) {
                tmpGrade.setReviewerGrade(60);
                outlierList.add(tmpGrade);
            }
            else nonOutlierList.add(tmpGrade);
        }

        List<GradeEntity> gradeList = listGradesByPresenter(nonOutlierList);
        gradeList.addAll(outlierList);
        gradeList.sort(Comparator.comparingLong(GradeEntity::getId));
        return gradeList;
    }

    private List<GradeEntity> mapScoresToGradesFromReviews(List<ReviewEntity> reviews) {
        Map<Long, FormEntity> formCache = new HashMap<>();
        List<GradeEntity> gradeEntities = new ArrayList<>();

        for (ReviewEntity review : reviews) {
            Long formId = review.getFormId();
            FormEntity form = formCache.computeIfAbsent(formId, formService::getFormById);

            Date reviewDate = form.getReviewDate();
            String reviewerId = form.getReviewerId();
            String presenterId = review.getPresenterId();
            String score = review.getScore();
            int presentOrder = review.getPresentOrder();
            int grade = SCORE_MAP.getOrDefault(score, 0);

            GradeEntity gradeEntity = GradeEntity
                    .builder()
                    .reviewDate(reviewDate)
                    .presenterId(presenterId)
                    .presentOrder(presentOrder)
                    .reviewerId(reviewerId)
                    .score(score)
                    .grade(grade)
                    .round(1)
                    .build();

            save(gradeEntity);
            gradeEntities.add(gradeEntity);
        }
        return gradeEntities;
    }

    private List<GradeEntity> listGradesByPresenter(List<GradeEntity> gradeList) {
        List<GradeEntity> result = new ArrayList<>();

        // 使用 Map 根據 presenterId 進行分組
        Map<String, List<GradeEntity>> gradesByPresenter = gradeList.stream()
                .collect(Collectors.groupingBy(GradeEntity::getPresenterId));

        // 遍歷每個 presenter 的分數，分別計算所有計算成績需要的統計數據
        for (Map.Entry<String, List<GradeEntity>> entry : gradesByPresenter.entrySet()) {
            List<GradeEntity> gradeListByPresenter = entry.getValue();

            // 計算報告者的標準差與平均
            DescriptiveStatistics stats = setStatistics(gradeListByPresenter);
            double presenterGrade = stats.getMean();
            double standardDeviation = GradeHelper.calculatePopulationStandardDeviation(stats);

            // 避免除以 0 的情況
            if (standardDeviation == 0) standardDeviation = 1;

            // 計算每位 reviewer 的成績
            calculateGrades(gradeListByPresenter, presenterGrade, standardDeviation);

            result.addAll(gradeListByPresenter);
        }

        return result;
    }

    private void calculateGrades(List<GradeEntity> gradeListByPresenter, double presenterGrade, double standardDeviation) {
        for (GradeEntity gradeDetail : gradeListByPresenter) {
            gradeDetail.setPresenterGrade(presenterGrade); // 設定 presenter 的平均分數
            gradeDetail.setStandardDeviation(standardDeviation);

            //  計算 Z-Score, reviewer's grade 以及判定是否為 outlier
            Statistic statistic = calculateReviewersStatistics(gradeDetail);

            gradeDetail.setZScore(statistic.getZScore());
            gradeDetail.setReviewerGrade(statistic.getReviewerGrade());

            //  只有第一輪計算成績才需判斷是否為 outlier
            if(gradeDetail.getRound() == 1) gradeDetail.setOutlier(statistic.getOutlier());
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

    private DescriptiveStatistics setStatistics(List<GradeEntity> gradeList) {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (GradeEntity gradeDetail : gradeList) {
            stats.addValue(gradeDetail.getGrade());
        }
        return stats;
    }

    protected abstract void save(GradeEntity gradeEntity);

//    protected abstract void updateReviewerDetailByReviewerIdAndPresenterId(
//           String reviewerId,
//           String presenterId,
//           double standardDeviation,
//           double zScore,
//           double reviewerGrade,
//           Boolean outlier,
//           int round
//    );

    protected abstract void updatePresenterGradeByPresenterId(GradeEntity grade);

    protected abstract void updateReviewerGradesByReviewerId(GradeEntity grade);
}
