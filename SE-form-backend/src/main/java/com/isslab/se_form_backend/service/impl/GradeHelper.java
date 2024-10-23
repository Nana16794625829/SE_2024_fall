package com.isslab.se_form_backend.service.impl;

import java.util.Map;

public class GradeHelper {
    public static double calculateZScore(int gradeByScore, double presenterGrade, double standardDeviation){
        return (gradeByScore - presenterGrade) / standardDeviation;
    }

    public static int getGradeGap(Map<String, Integer> SCORE_MAP){
        return SCORE_MAP.get("A") - SCORE_MAP.get("C");
    }

    public static double calculateReviewerGrade(double zScore, int gradeGap){
        return 100 - (Math.abs(zScore) / 3) * gradeGap;
    }

    public static Boolean isOutlier(double zScore, double zScoreThreshold){
        return Math.abs(zScore) > zScoreThreshold;
    }
}