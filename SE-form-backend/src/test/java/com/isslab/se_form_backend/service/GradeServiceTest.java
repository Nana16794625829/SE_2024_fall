package com.isslab.se_form_backend.service;

import com.isslab.se_form_backend.entity.GradeEntity;
import com.isslab.se_form_backend.service.impl.MockGradeService;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class GradeServiceTest {

    @Test
    public void test(){
        MockGradeService gradeService = new MockGradeService(null, null);
        //計算成績
        List<GradeEntity> answerGradeList = gradeService.calculateGrades(gradeList());

        //reviewer1 : presenter1 答案
        GradeEntity answerGrade1 = answerGradeList.get(0);
        assertThat(answerGrade1.getId()).isEqualTo(73);
        assertThat(answerGrade1.getReviewerId()).isEqualTo("113522010");
        assertThat(answerGrade1.getReviewerGrade()).isEqualTo(95.28595479208968);
        assertThat(answerGrade1.getPresenterGrade()).isEqualTo(80.0);

        //reviewer1 : presenter2 答案
        GradeEntity answerGrade2 = answerGradeList.get(1);
        assertThat(answerGrade2.getId()).isEqualTo(74);
        assertThat(answerGrade2.getReviewerId()).isEqualTo("113522010");
        assertThat(answerGrade2.getReviewerGrade()).isEqualTo(95.28595479208968);
        assertThat(answerGrade2.getPresenterGrade()).isEqualTo(75.0);

        //reviewer1 : presenter3 答案
        GradeEntity answerGrade3 = answerGradeList.get(2);
        assertThat(answerGrade3.getId()).isEqualTo(75);
        assertThat(answerGrade3.getReviewerId()).isEqualTo("113522010");
        assertThat(answerGrade3.getReviewerGrade()).isEqualTo(100.0);
        assertThat(answerGrade3.getPresenterGrade()).isEqualTo(80.0);

        //reviewer2 : presenter1 答案
        GradeEntity answerGrade4 = answerGradeList.get(3);
        assertThat(answerGrade4.getId()).isEqualTo(76);
        assertThat(answerGrade4.getReviewerId()).isEqualTo("113522099");
        assertThat(answerGrade4.getReviewerGrade()).isEqualTo(95.28595479208968);
        assertThat(answerGrade4.getPresenterGrade()).isEqualTo(80.0);

        //reviewer2 : presenter2 答案
        GradeEntity answerGrade5 = answerGradeList.get(4);
        assertThat(answerGrade5.getId()).isEqualTo(77);
        assertThat(answerGrade5.getReviewerId()).isEqualTo("113522099");
        assertThat(answerGrade5.getReviewerGrade()).isEqualTo(95.28595479208968);
        assertThat(answerGrade5.getPresenterGrade()).isEqualTo(75.0);

        //reviewer2 : presenter3 答案
        GradeEntity answerGrade6 = answerGradeList.get(5);
        assertThat(answerGrade6.getId()).isEqualTo(78);
        assertThat(answerGrade6.getReviewerId()).isEqualTo("113522099");
        assertThat(answerGrade6.getReviewerGrade()).isEqualTo(100.0);
        assertThat(answerGrade6.getPresenterGrade()).isEqualTo(80.0);
    }


    private List<GradeEntity> gradeList(){
        GradeEntity grade1 = new GradeEntity();
        grade1.setId(73L);
        grade1.setReviewDate(new Date());  // 使用當前時間
        grade1.setPresenterId("113500001");
        grade1.setPresentOrder(1);
        grade1.setReviewerId("113522010");
        grade1.setScore("A");
        grade1.setGrade(90);
        grade1.setStandardDeviation(0.0);
        grade1.setZScore(0.0);
        grade1.setReviewerGrade(0.0);
        grade1.setPresenterGrade(0.0);
        grade1.setOutlier(false);
        grade1.setRound(0);

        GradeEntity grade2 = new GradeEntity();
        grade2.setId(74L);
        grade2.setReviewDate(new Date());
        grade2.setPresenterId("113500002");
        grade2.setPresentOrder(1);
        grade2.setReviewerId("113522010");
        grade2.setScore("C");
        grade2.setGrade(70);
        grade2.setStandardDeviation(0.0);
        grade2.setZScore(0.0);
        grade2.setReviewerGrade(0.0);
        grade2.setPresenterGrade(0.0);
        grade2.setOutlier(false);
        grade2.setRound(0);

        GradeEntity grade3 = new GradeEntity();
        grade3.setId(75L);
        grade3.setReviewDate(new Date());
        grade3.setPresenterId("113500003");
        grade3.setPresentOrder(1);
        grade3.setReviewerId("113522010");
        grade3.setScore("B");
        grade3.setGrade(80);
        grade3.setStandardDeviation(0.0);
        grade3.setZScore(0.0);
        grade3.setReviewerGrade(0.0);
        grade3.setPresenterGrade(0.0);
        grade3.setOutlier(false);
        grade3.setRound(0);

        GradeEntity grade4 = new GradeEntity();
        grade4.setId(76L);
        grade4.setReviewDate(new Date());
        grade4.setPresenterId("113500001");
        grade4.setPresentOrder(1);
        grade4.setReviewerId("113522099");
        grade4.setScore("C");
        grade4.setGrade(70);
        grade4.setStandardDeviation(0.0);
        grade4.setZScore(0.0);
        grade4.setReviewerGrade(0.0);
        grade4.setPresenterGrade(0.0);
        grade4.setOutlier(false);
        grade4.setRound(0);

        GradeEntity grade5 = new GradeEntity();
        grade5.setId(77L);
        grade5.setReviewDate(new Date());
        grade5.setPresenterId("113500002");
        grade5.setPresentOrder(1);
        grade5.setReviewerId("113522099");
        grade5.setScore("B");
        grade5.setGrade(80);
        grade5.setStandardDeviation(0.0);
        grade5.setZScore(0.0);
        grade5.setReviewerGrade(0.0);
        grade5.setPresenterGrade(0.0);
        grade5.setOutlier(false);
        grade5.setRound(0);

        GradeEntity grade6 = new GradeEntity();
        grade6.setId(78L);
        grade6.setReviewDate(new Date());
        grade6.setPresenterId("113500003");
        grade6.setPresentOrder(1);
        grade6.setReviewerId("113522099");
        grade6.setScore("B");
        grade6.setGrade(80);
        grade6.setStandardDeviation(0.0);
        grade6.setZScore(0.0);
        grade6.setReviewerGrade(0.0);
        grade6.setPresenterGrade(0.0);
        grade6.setOutlier(false);
        grade6.setRound(0);

        return List.of(grade1, grade2, grade3, grade4, grade5, grade6);
    }

}