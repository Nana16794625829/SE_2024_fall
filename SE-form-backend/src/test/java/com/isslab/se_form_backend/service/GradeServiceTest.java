package com.isslab.se_form_backend.service;

import com.isslab.se_form_backend.entity.FormScoreRecordEntity;
import com.isslab.se_form_backend.service.impl.MockGradeService;
import com.isslab.se_form_backend.service.util.FormScoreRecordLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GradeServiceTest {

    private List<FormScoreRecordEntity> allRecordsNoOutlier;
    private List<FormScoreRecordEntity> bScoreRecords;

    @BeforeAll
    public void setUp() throws IOException {
        List<FormScoreRecordEntity> allRecords = FormScoreRecordLoader.loadFromCsv("/test_data/sample.csv");
        allRecordsNoOutlier = FormScoreRecordLoader.loadFromCsv("/test_data/sample_no_outlier.csv");
        bScoreRecords = FormScoreRecordLoader.filterByScore(allRecords, "B");
    }

    @Test
    public void testCalculateGrades() throws IOException {
        MockGradeService gradeService = new MockGradeService();
        Map<String, Double> answerGradeListOnServiceClass = gradeService.calculateGrade();
        assertThat(answerGradeListOnServiceClass.get("113525009")).isEqualTo(60);
    }

    @Test
    public void testCalculateGradesNoOutliers(){
        MockGradeService gradeService = new MockGradeService();
        //計算成績
        Map<String, Double> answerGradeListOnServiceClass = gradeService.calculateGrade(allRecordsNoOutlier);

        Double answerGradePresenter1 = answerGradeListOnServiceClass.get("113525009");
        assertThat(answerGradePresenter1).isEqualTo(96.2);

        Double answerGradePresenter2 = answerGradeListOnServiceClass.get("110502545");
        assertThat(answerGradePresenter2).isEqualTo(96.2);

        Double answerGradePresenter3 = answerGradeListOnServiceClass.get("113525010");
        assertThat(answerGradePresenter3).isEqualTo(88.5);
    }

    @Test
    public void testCalculateGradesAllB(){
        MockGradeService gradeService = new MockGradeService();
        //計算成績
        Map<String, Double> answerGradeListOnServiceClass = gradeService.calculateGrade(bScoreRecords);

        Double answerGradePresenter1 = answerGradeListOnServiceClass.get("110502516");
        assertThat(answerGradePresenter1).isEqualTo(100);

        Double answerGradePresenter2 = answerGradeListOnServiceClass.get("109502004");
        assertThat(answerGradePresenter2).isEqualTo(100);
    }
}