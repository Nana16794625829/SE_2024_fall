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
        List<GradeEntity> gradeList = createGradeList(reviews);
        return calculateGrades(gradeList);
    }

    protected abstract void save(GradeEntity gradeEntity);

    /*
    *
    * */
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
            save(gradeEntity);
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

    protected abstract void updateReviewerDetailByReviewerIdAndPresenterId(
           String reviewerId,
           String presenterId,
           double standardDeviation,
           double zScore,
           double reviewerGrade,
           Boolean outlier,
           int round);

    private Statistic calculateReviewersGrades(GradeEntity gradeDetail, double standardDeviation) {
        double zScore = GradeHelper.calculateZScore(gradeDetail.getGrade(),
                gradeDetail.getPresenterGrade(),
                standardDeviation);

        int gradeGap = GradeHelper.getGradeGap(SCORE_MAP);

        double reviewerGrade = GradeHelper.calculateReviewerGrade(zScore, gradeGap);

        Boolean outlier = GradeHelper.isOutlier(zScore, zScoreThreshold);

        String presenterId = gradeDetail.getPresenterId();
        String reviewerId = gradeDetail.getReviewerId();

        updateReviewerDetailByReviewerIdAndPresenterId(reviewerId, presenterId, standardDeviation, zScore, reviewerGrade, outlier, 1);

        //回傳統計值
        return Statistic.builder()
                .zScore(zScore)
                .gradeGap(gradeGap)
                .reviewerGrade(reviewerGrade)
                .outlier(outlier)
                .build();
    }


    protected abstract void updatePresenterGradeByPresenterId(double presenterGrade, String presenterId);

    // 將 GradeEntity 依照 presenterId 分組並計算成績
    public List<GradeEntity> calculateGrades(List<GradeEntity> gradeList) {
        List<GradeEntity> result = new ArrayList<>();

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
                updatePresenterGradeByPresenterId(presenterGrade, presenterId);
                gradeDetail.setPresenterGrade(presenterGrade); // 設定 presenter 的平均分數
                gradeDetail.setStandardDeviation(standardDeviation);

                Statistic statistic = calculateReviewersGrades(gradeDetail, standardDeviation); // 計算 Z-Score 等數據

                gradeDetail.setZScore(statistic.getZScore());
                gradeDetail.setGrade(statistic.getGradeGap());
                gradeDetail.setReviewerGrade(statistic.getReviewerGrade());
                gradeDetail.setOutlier(statistic.getOutlier());

                result.add(gradeDetail);
            }
        }

        result.sort(Comparator.comparingLong(GradeEntity::getId));
        return result;
    }
}
