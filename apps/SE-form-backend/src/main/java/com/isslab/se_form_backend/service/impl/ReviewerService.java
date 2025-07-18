package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.entity.ReviewerGradeEntity;
import com.isslab.se_form_backend.entity.id.ReviewerGradeEntityId;
import com.isslab.se_form_backend.model.GradeInput;
import com.isslab.se_form_backend.repository.ReviewerRepository;
import com.isslab.se_form_backend.service.AbstractStudentRoleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReviewerService extends AbstractStudentRoleService {

    private final ReviewerRepository repository;
    private static final double BASIC_GRADE = 75.0;

    public ReviewerService(ReviewerRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveAllGrades(List<GradeInput> gradeList) {
        // 組合 composite key
        List<ReviewerGradeEntityId> keys = gradeList.stream()
                .map(g -> new ReviewerGradeEntityId(g.getStudentId(), g.getPresenterId(), g.getWeek()))
                .toList();

        List<ReviewerGradeEntity> existing = repository.findAllById(keys);
        Map<String, ReviewerGradeEntity> existingMap = buildExistingMap(existing);
        List<ReviewerGradeEntity> toSave = listAllReviewersToSaveGrade(gradeList, existingMap);

        repository.saveAll(toSave);
    }

    @Override
    public void saveGradeToStudent(String studentId, String week, double grade) {
        ReviewerGradeEntity reviewerGradeEntity = ReviewerGradeEntity.builder()
                .reviewerId(studentId)
                .week(week)
                .grade(grade)
                .build();

        repository.save(reviewerGradeEntity);
    }

    @Override
    public List<Double> getGradesByIdAndWeek(String studentId, String week) {
        return repository.getGradesByReviewerIdAndWeek(studentId, week);
    }

    @Override
    public void deleteGradeByIdAndWeek(String studentId, String week) {
        repository.deleteByReviewerIdAndWeek(studentId, week);
    }

    @Override
    public double getBasicGrade() {
        return BASIC_GRADE;
    }

    public List<ReviewerGradeEntity> findNonAttendeeByWeek(String week){
        return repository.findAllByWeekAndGradeIsNull(week);
    }

    private Map<String, ReviewerGradeEntity> buildExistingMap(List<ReviewerGradeEntity> existing) {
        return existing.stream()
                .collect(Collectors.toMap(
                        e -> e.getReviewerId() + "-" + e.getPresenterId() + "-" + e.getWeek(),
                        Function.identity(),
                        (existed, replacement) -> replacement
                ));
    }

    private List<ReviewerGradeEntity> listAllReviewersToSaveGrade(List<GradeInput> gradeList, Map<String, ReviewerGradeEntity> existingMap) {
        List<ReviewerGradeEntity> toSave = new ArrayList<>();

        for (GradeInput g : gradeList) {
            ReviewerGradeEntity reviewerGradeEntity = handleReviewerByExistingStatus(g, existingMap);
            toSave.add(reviewerGradeEntity);
        }

        return toSave;
    }

    private ReviewerGradeEntity handleReviewerByExistingStatus(GradeInput g, Map<String, ReviewerGradeEntity> existingMap) {
        String key = g.getStudentId() + "-" + g.getPresenterId() + "-" + g.getWeek();

        if (existingMap.containsKey(key)) {
            ReviewerGradeEntity entity = existingMap.get(key);
            entity.setGrade(g.getGrade()); // 更新 grade
            return entity;
        } else {
            return ReviewerGradeEntity.builder()
                    .reviewerId(g.getStudentId())
                    .presenterId(g.getPresenterId())
                    .week(g.getWeek())
                    .grade(g.getGrade())
                    .build();
        }
    }

}
