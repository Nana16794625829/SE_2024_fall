package com.isslab.se_form_backend.service.impl;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.Collections;
import java.util.Map;

public class GradeHelper {
    public static double calculateZScore(int gradeByScore, double presenterGrade, double standardDeviation){
        return (gradeByScore - presenterGrade) / standardDeviation;
    }

    public static double getGradeGap(Map<String, Double> scoreMap) {
        if (scoreMap.isEmpty()) return 0.0;

        double max = Collections.max(scoreMap.values());
        double min = Collections.min(scoreMap.values());

        return max - min;
    }

    public static double calculateReviewerGrade(double zScore, int gradeGap){
        return 100 - (Math.abs(zScore) / 3) * gradeGap;
    }

    public static Boolean isOutlier(double zScore, double zScoreThreshold){
        return Math.abs(zScore) > zScoreThreshold;
    }

    public static double calculatePopulationStandardDeviation(DescriptiveStatistics stats) {
        double variance = stats.getVariance() * ((stats.getN() - 1.0) / stats.getN());
        return Math.sqrt(variance);
    }
}