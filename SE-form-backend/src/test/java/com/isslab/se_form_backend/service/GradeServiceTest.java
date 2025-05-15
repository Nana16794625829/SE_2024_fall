package com.isslab.se_form_backend.service;

import com.isslab.se_form_backend.entity.FormScoreRecordEntity;
import com.isslab.se_form_backend.entity.FormSubmissionEntity;
import com.isslab.se_form_backend.entity.PresenterEntity;
import com.isslab.se_form_backend.entity.ReviewerEntity;
import com.isslab.se_form_backend.service.impl.MockGradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

class GradeServiceTest {

//    @Mock
//    private IFormService formService;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//
//        // 模擬 formService 的方法行為
//        when(formService.getFormById(1L)).thenReturn(new FormSubmissionEntity("113522010", "1", 1L, "2025/05/13 14:00", ""));
//    }

    @Test
    public void testCalculateGradesRoundOne(){
        MockGradeService gradeService = new MockGradeService();
        //計算成績
        Map<String, Double> answerGradeListOnServiceClass = gradeService.calculateGrade(getFormScoreRecords());
//        List<ReviewerEntity> answerGradeListDayClass = gradeService.calculateGrade(getFormScoreRecords());

        //Presenter1 答案
        Double answerGradePresenter1 = answerGradeListOnServiceClass.get("113522010");
        assertThat(answerGradePresenter1).isEqualTo(93.33333333333333);
//        assertThat(answerGradePresenter1).isEqualTo(60.0);

//        PresenterEntity answerGradePresenter1 = answerGradeListOnServiceClass.get("0");
//        assertThat(answerGradePresenter1.getWeek()).isEqualTo("1");
//        assertThat(answerGradePresenter1.getPresenterId()).isEqualTo("113500001");
//        assertThat(answerGradePresenter1.getGrade()).isEqualTo(80.0);

//        //Presenter2 答案
//        PresenterEntity answerGradePresenter2 = answerGradeListOnServiceClass.get(1);
//        assertThat(answerGradePresenter2.getWeek()).isEqualTo("1");
//        assertThat(answerGradePresenter2.getPresenterId()).isEqualTo("113500002");
//        assertThat(answerGradePresenter2.getGrade()).isEqualTo(80.0);
//
//        //Presenter3 答案
//        PresenterEntity answerGradePresenter3 = answerGradeListOnServiceClass.get(2);
//        assertThat(answerGradePresenter3.getWeek()).isEqualTo("1");
//        assertThat(answerGradePresenter3.getPresenterId()).isEqualTo("113500003");
//        assertThat(answerGradePresenter3.getGrade()).isEqualTo(75.0);
//
//        //reviewer1 : presenter1 答案
//        ReviewerEntity answerGradeR1P1 = answerGradeListDayClass.get(0);
//        assertThat(answerGradeR1P1.getWeek()).isEqualTo("1");
//        assertThat(answerGradeR1P1.getReviewerId()).isEqualTo("113522010");
//        assertThat(answerGradeR1P1.getGrade()).isEqualTo(95.28595479208968);
//
//        //reviewer1 : presenter2 答案
////        GradeEntity answerGrade2 = answerGradeList.get(1);
//        ReviewerEntity answerGradeR1P2 = answerGradeListDayClass.get(2);
//        assertThat(answerGradeR1P2.getWeek()).isEqualTo(1);
//        assertThat(answerGradeR1P2.getReviewerId()).isEqualTo("113522010");
//        assertThat(answerGradeR1P2.getGrade()).isEqualTo(95.28595479208968);
//
//        //reviewer1 : presenter3 答案
////        GradeEntity answerGrade3 = answerGradeList.get(2);
//        ReviewerEntity answerGradeR1P3 = answerGradeListDayClass.get(4);
//        assertThat(answerGradeR1P3.getWeek()).isEqualTo(1);
//        assertThat(answerGradeR1P3.getReviewerId()).isEqualTo("113522010");
//        assertThat(answerGradeR1P3.getGrade()).isEqualTo(100.0);
//
//        //reviewer2 : presenter1 答案
////        GradeEntity answerGrade4 = answerGradeList.get(3);
//        ReviewerEntity answerGradeR2P1 = answerGradeListDayClass.get(1);
//        assertThat(answerGradeR2P1.getWeek()).isEqualTo(1);
//        assertThat(answerGradeR2P1.getReviewerId()).isEqualTo("113522099");
//        assertThat(answerGradeR2P1.getGrade()).isEqualTo(95.28595479208968);
//
//        //reviewer2 : presenter2 答案
////        GradeEntity answerGrade5 = answerGradeList.get(4);
//        ReviewerEntity answerGradeR2P2 = answerGradeListDayClass.get(3);
//        assertThat(answerGradeR2P2.getWeek()).isEqualTo(1);
//        assertThat(answerGradeR2P2.getReviewerId()).isEqualTo("113522099");
//        assertThat(answerGradeR2P2.getGrade()).isEqualTo(95.28595479208968);
//
//        //reviewer2 : presenter3 答案
//        ReviewerEntity answerGradeR2P3 = answerGradeListDayClass.get(5);
//        assertThat(answerGradeR2P3.getWeek()).isEqualTo(1);
//        assertThat(answerGradeR2P3.getReviewerId()).isEqualTo("113522099");
//        assertThat(answerGradeR2P3.getGrade()).isEqualTo(100.0);
    }


//    private List<GradeEntity> gradeList(){
//        GradeEntity grade1 = new GradeEntity();
//        grade1.setId(73L);
//        grade1.setReviewDate(new Date());  // 使用當前時間
//        grade1.setPresenterId("113500001");
//        grade1.setPresentOrder(1);
//        grade1.setReviewerId("113522010");
//        grade1.setScore("A");
//        grade1.setGrade(90);
//        grade1.setStandardDeviation(0.0);
//        grade1.setZScore(0.0);
//        grade1.setReviewerGrade(0.0);
//        grade1.setPresenterGrade(0.0);
//        grade1.setOutlier(false);
//        grade1.setRound(0);
//
//        GradeEntity grade2 = new GradeEntity();
//        grade2.setId(74L);
//        grade2.setReviewDate(new Date());
//        grade2.setPresenterId("113500002");
//        grade2.setPresentOrder(1);
//        grade2.setReviewerId("113522010");
//        grade2.setScore("C");
//        grade2.setGrade(70);
//        grade2.setStandardDeviation(0.0);
//        grade2.setZScore(0.0);
//        grade2.setReviewerGrade(0.0);
//        grade2.setPresenterGrade(0.0);
//        grade2.setOutlier(false);
//        grade2.setRound(0);
//
//        GradeEntity grade3 = new GradeEntity();
//        grade3.setId(75L);
//        grade3.setReviewDate(new Date());
//        grade3.setPresenterId("113500003");
//        grade3.setPresentOrder(1);
//        grade3.setReviewerId("113522010");
//        grade3.setScore("B");
//        grade3.setGrade(80);
//        grade3.setStandardDeviation(0.0);
//        grade3.setZScore(0.0);
//        grade3.setReviewerGrade(0.0);
//        grade3.setPresenterGrade(0.0);
//        grade3.setOutlier(false);
//        grade3.setRound(0);
//
//        GradeEntity grade4 = new GradeEntity();
//        grade4.setId(76L);
//        grade4.setReviewDate(new Date());
//        grade4.setPresenterId("113500001");
//        grade4.setPresentOrder(1);
//        grade4.setReviewerId("113522099");
//        grade4.setScore("C");
//        grade4.setGrade(70);
//        grade4.setStandardDeviation(0.0);
//        grade4.setZScore(0.0);
//        grade4.setReviewerGrade(0.0);
//        grade4.setPresenterGrade(0.0);
//        grade4.setOutlier(false);
//        grade4.setRound(0);
//
//        GradeEntity grade5 = new GradeEntity();
//        grade5.setId(77L);
//        grade5.setReviewDate(new Date());
//        grade5.setPresenterId("113500002");
//        grade5.setPresentOrder(1);
//        grade5.setReviewerId("113522099");
//        grade5.setScore("B");
//        grade5.setGrade(80);
//        grade5.setStandardDeviation(0.0);
//        grade5.setZScore(0.0);
//        grade5.setReviewerGrade(0.0);
//        grade5.setPresenterGrade(0.0);
//        grade5.setOutlier(false);
//        grade5.setRound(0);
//
//        GradeEntity grade6 = new GradeEntity();
//        grade6.setId(78L);
//        grade6.setReviewDate(new Date());
//        grade6.setPresenterId("113500003");
//        grade6.setPresentOrder(1);
//        grade6.setReviewerId("113522099");
//        grade6.setScore("B");
//        grade6.setGrade(80);
//        grade6.setStandardDeviation(0.0);
//        grade6.setZScore(0.0);
//        grade6.setReviewerGrade(0.0);
//        grade6.setPresenterGrade(0.0);
//        grade6.setOutlier(false);
//        grade6.setRound(0);
//
//        return List.of(grade1, grade2, grade3, grade4, grade5, grade6);
//    }

    public List<FormScoreRecordEntity> getFormScoreRecords() {
//        FormScoreRecordEntity scoreRecord1 = new FormScoreRecordEntity(1L, 1L, "A", "113522010", "113500001");
//        FormScoreRecordEntity scoreRecord2 = new FormScoreRecordEntity(1L, 1L, "C", "113522010", "113500002");
//        FormScoreRecordEntity scoreRecord3 = new FormScoreRecordEntity(1L, 1L, "B", "113522010", "113500003");
//        FormScoreRecordEntity scoreRecord4 = new FormScoreRecordEntity(1L, 1L, "C", "113522099", "113500001");
//        FormScoreRecordEntity scoreRecord5 = new FormScoreRecordEntity(1L, 1L, "B", "113522099", "113500002");
//        FormScoreRecordEntity scoreRecord6 = new FormScoreRecordEntity(1L, 1L, "B", "113522099", "113500003");
        FormScoreRecordEntity scoreRecord1 = new FormScoreRecordEntity(1L, 1L, "A", "113522010", "113500001");
        FormScoreRecordEntity scoreRecord2 = new FormScoreRecordEntity(1L, 1L, "C", "113522011", "113500001");
        FormScoreRecordEntity scoreRecord3 = new FormScoreRecordEntity(1L, 1L, "C", "113522012", "113500001");
        FormScoreRecordEntity scoreRecord4 = new FormScoreRecordEntity(1L, 1L, "C", "113522013", "113500001");
        FormScoreRecordEntity scoreRecord5 = new FormScoreRecordEntity(1L, 1L, "C", "113522014", "113500001");
        FormScoreRecordEntity scoreRecord6 = new FormScoreRecordEntity(1L, 1L, "C", "113522015", "113500001");
        FormScoreRecordEntity scoreRecord7 = new FormScoreRecordEntity(1L, 1L, "C", "113522016", "113500001");
        FormScoreRecordEntity scoreRecord8 = new FormScoreRecordEntity(1L, 1L, "C", "113522017", "113500001");
        FormScoreRecordEntity scoreRecord9 = new FormScoreRecordEntity(1L, 1L, "C", "113522018", "113500001");

        List<FormScoreRecordEntity> scoreRecordEntities = new ArrayList<>();
        scoreRecordEntities.add(scoreRecord1);
        scoreRecordEntities.add(scoreRecord2);
//        scoreRecordEntities.add(scoreRecord3);
//        scoreRecordEntities.add(scoreRecord4);
//        scoreRecordEntities.add(scoreRecord5);
//        scoreRecordEntities.add(scoreRecord6);
//        scoreRecordEntities.add(scoreRecord7);
//        scoreRecordEntities.add(scoreRecord8);
//        scoreRecordEntities.add(scoreRecord9);

        return scoreRecordEntities;
    }
}