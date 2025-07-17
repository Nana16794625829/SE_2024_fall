package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.entity.PresenterGradeEntity;
import com.isslab.se_form_backend.helper.exception.InvalidGradeCalculationException;
import com.isslab.se_form_backend.model.GradeInput;
import com.isslab.se_form_backend.model.Presenter;
import com.isslab.se_form_backend.repository.PresenterRepository;
import com.isslab.se_form_backend.service.AbstractStudentRoleService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class PresenterService extends AbstractStudentRoleService {

    private final PresenterRepository repository;
    private static final double BASIC_GRADE = 15.0;

    public PresenterService(PresenterRepository repository){
        this.repository = repository;
    }

    @Override
    @Transactional
    public void saveAllGrades(List<GradeInput> gradeList) {
        // Step 1: 查出所有對應的 existing entries
        List<String> ids = gradeList.stream().map(GradeInput::getStudentId).toList();
        List<String> weeks = gradeList.stream().map(GradeInput::getWeek).toList();
        List<PresenterGradeEntity> existingEntities = repository.findAllByPresenterIdInAndWeekIn(ids, weeks);

        // Step 2: 建立查詢對照表
        Map<String, PresenterGradeEntity> existingMap = buildExistingMap(existingEntities);

        // Step 3: 建立更新清單
        List<PresenterGradeEntity> toSave = listAllPresentersToSaveGrade(gradeList, existingMap);

        repository.saveAll(toSave);
    }


    @Override
    public void saveGradeToStudent(String studentId, String week, double grade) {
        PresenterGradeEntity presenterGradeEntity = getPresenterEntityByStudentIdAndWeek(studentId, week);
        presenterGradeEntity.setGrade(grade);

        repository.save(presenterGradeEntity);
    }

    @Override
    public List<Double> getGradesByIdAndWeek(String studentId, String week) {
        return repository.getGradesByPresenterIdAndWeek(studentId, week);
    }

    @Override
    public void deleteGradeByIdAndWeek(String studentId, String week) {
        repository.deleteByPresenterIdAndWeek(studentId, week);
    }

    @Override
    public double getBasicGrade() {
        return BASIC_GRADE;
    }

    public String getPresenterIdByWeekAndOrder(String week, int order) {
        PresenterGradeEntity presenter = repository.getByWeekAndPresentOrder(week, order);

        if(presenter == null){
            throw new InvalidGradeCalculationException("請先記錄 Presenter 再計算成績");
        }

        return presenter.getPresenterId();
    }

    public void addPresenter(Presenter presenter) {
        PresenterGradeEntity presenterGradeEntity = PresenterGradeEntity.builder()
                .presenterId(presenter.getPresenterId())
                .presentOrder(Integer.parseInt(presenter.getPresentOrder()))
                .week(presenter.getPresentWeek())
                .build();

        repository.save(presenterGradeEntity);
    }

    public void addPresentersByWeek(List<Presenter> presenters){
        for(Presenter presenter : presenters){
            addPresenter(presenter);
        }
    }

    private PresenterGradeEntity getPresenterEntityByStudentIdAndWeek(String studentId, String week) {
        return repository.getByPresenterIdAndWeek(studentId, week);
    }

    private Map<String, PresenterGradeEntity> buildExistingMap(List<PresenterGradeEntity> existingEntities) {
        Map<String, PresenterGradeEntity> existingMap = new HashMap<>();
        for (PresenterGradeEntity entity : existingEntities) {
            String key = entity.getPresenterId() + "-" + entity.getWeek();
            existingMap.put(key, entity);
        }

        return existingMap;
    }

    private List<PresenterGradeEntity> listAllPresentersToSaveGrade(List<GradeInput> gradeList, Map<String, PresenterGradeEntity> existingMap) {
        List<PresenterGradeEntity> toSave = new ArrayList<>();

        for (GradeInput g : gradeList) {
            PresenterGradeEntity presenterGradeEntity = handlePresenterByExistingStatus(g, existingMap);
            if(presenterGradeEntity != null) toSave.add(presenterGradeEntity);
        }

        return toSave;
    }

    private PresenterGradeEntity handlePresenterByExistingStatus(GradeInput g, Map<String, PresenterGradeEntity> existingMap) {
        String key = g.getStudentId() + "-" + g.getWeek();

        if (existingMap.containsKey(key)) {
            PresenterGradeEntity entity = existingMap.get(key);
            entity.setGrade(g.getGrade()); // 更新 grade
            return entity;
        } else {
            log.info("報告者 {} 目前不需紀錄成績", g.getStudentId());
            return null;
        }
    }
}
