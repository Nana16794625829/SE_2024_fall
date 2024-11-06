package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.entity.GradeEntity;
import com.isslab.se_form_backend.entity.StudentGradeEntity;
import com.isslab.se_form_backend.entity.WeeklyGradeEntity;
import com.isslab.se_form_backend.model.Grade;
import com.isslab.se_form_backend.model.StudentGrade;
import com.isslab.se_form_backend.model.WeeklyGrade;
import com.isslab.se_form_backend.repository.GradeRepository;
import com.isslab.se_form_backend.repository.WeeklyGradeRepository;
import com.isslab.se_form_backend.service.AbstractGradeService;
import com.isslab.se_form_backend.service.IFormService;

import java.util.List;
import java.util.stream.Collectors;

public class GradeService extends AbstractGradeService {
    private final GradeRepository gradeRepository;
    private final WeeklyGradeRepository weeklyGradeRepository;
    public GradeService(GradesToCSVService gradesToCSVService, IFormService formService, GradeRepository gradeRepository, WeeklyGradeRepository weeklyGradeRepository) {
        super(gradesToCSVService, formService);
        this.gradeRepository = gradeRepository;
        this.weeklyGradeRepository = weeklyGradeRepository;
    }

    @Override
    protected void addUndergraduatesWeeklyGrades(String week, List<StudentGrade> studentGrades) {
        WeeklyGradeEntity weeklyGrade = WeeklyGradeEntity.builder()
                .week(week)
                .build();

        for (StudentGrade grade : studentGrades) {
            StudentGradeEntity studentGrade = StudentGradeEntity.builder()
                    .studentId(grade.getStudentId())
                    .grades(grade.getGrades())
                    .build();
            weeklyGrade.addStudentGrade(studentGrade);
        }

        weeklyGradeRepository.save(weeklyGrade);
    }

    @Override
    protected void addOnServiceWeeklyGrades(String week, StudentGrade studentGrade) {
        WeeklyGradeEntity weeklyGrade = WeeklyGradeEntity.builder()
                .week(week)
                .build();

        StudentGradeEntity studentGradeEntity = StudentGradeEntity.builder()
                .studentId(studentGrade.getStudentId())
                .grades(studentGrade.getGrades())
                .build();
        weeklyGrade.addStudentGrade(studentGradeEntity);

        weeklyGradeRepository.save(weeklyGrade);
    }

    @Override
    protected WeeklyGrade getGradesByWeek(String week) {
        WeeklyGradeEntity weeklyGrade = weeklyGradeRepository.findByWeek(week)
                .orElseThrow(() -> new RuntimeException("Week not found: " + week));

        List<StudentGrade> studentGrades = weeklyGrade.getStudentGrades().stream()
                .map(grade -> StudentGrade.builder()
                        .studentId(grade.getStudentId())
                        .grades(grade.getGrades())
                        .build())
                .collect(Collectors.toList());

        return WeeklyGrade.builder()
                .week(week)
                .studentGrades(studentGrades)
                .build();
    }

//    @Override
//    protected WeeklyGrade getOnServiceGradesByWeek(String week) {
//        Grade grade1 = Grade.builder().studentId("113500001").grade(80).build();
//        Grade grade2 = Grade.builder().studentId("113500002").grade(78.41).build();
//        Grade grade3 = Grade.builder().studentId("113500003").grade(88.5).build();
//        return List.of(grade1, grade2, grade3);
//    }

    @Override
    protected void save(GradeEntity gradeEntity) {
        gradeRepository.save(gradeEntity);
    }

//    @Override
//    protected void updateReviewerDetailByReviewerIdAndPresenterId(String reviewerId, String presenterId, double standardDeviation, double zScore, double reviewerGrade, Boolean outlier, int round) {
//        gradeRepository.updateReviewerDetailByReviewerIdAndPresenterId(reviewerId, presenterId, standardDeviation, zScore, reviewerGrade, outlier, 1);
//    }

    @Override
    protected void updatePresenterGradeByPresenterId(GradeEntity grade) {
        gradeRepository.updatePresenterGradeByPresenterId(grade.getPresenterGrade(), grade.getPresenterId());
    }

    @Override
    protected void updateReviewerGradesByReviewerId(GradeEntity grade) {
        gradeRepository.updatePresenterGradeByPresenterId(grade.getReviewerGrade(), grade.getReviewerId());
    }
}
