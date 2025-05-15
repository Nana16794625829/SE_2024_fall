//package com.isslab.se_form_backend.service.impl;
//
//import org.junit.jupiter.api.Test;
//
//import java.util.Map;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//class GradeHelperTest {
//    @Test
//    public void calculateZScoreTest(){
//        Double answer = GradeHelper.calculateZScore(90, 80, 2);
//        assertThat(answer).isEqualTo(5);
//    }
//
//    @Test
//    public void  getGradeGapTest(){
//        int answer = GradeHelper.getGradeGap(Map.of(
//                "A", 90,
//                "B", 80,
//                "C", 70
//        ));
//
//        assertThat(answer).isEqualTo(20);
//    }
//
//    @Test
//    public void calculateReviewerGradeTest(){
//        double answer = GradeHelper.calculateReviewerGrade(1.5, 20);
//        assertThat(answer).isEqualTo(90);
//    }
//
//    @Test
//    public void isOutlierTest(){
//        double zScore = 2;
//        double zScoreThreshold = 2.5;
//        Boolean answer = GradeHelper.isOutlier(zScore, zScoreThreshold);
//
//        assertThat(answer).isEqualTo(Boolean.FALSE);
//    }
//}