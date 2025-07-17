package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.entity.FormScoreRecordEntity;
import com.isslab.se_form_backend.entity.FormSubmissionEntity;
import com.isslab.se_form_backend.model.FormScoreRecord;
import com.isslab.se_form_backend.model.FormSubmission;
import com.isslab.se_form_backend.service.AbstractFormScoreRecordService;
import com.isslab.se_form_backend.service.AbstractFormSubmissionService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FormProcessingService {

    private final AbstractFormSubmissionService formSubmissionService;
    private final AbstractFormScoreRecordService formScoreRecordService;

    public FormProcessingService(AbstractFormSubmissionService formSubmissionService, AbstractFormScoreRecordService formScoreRecordService) {
        this.formSubmissionService = formSubmissionService;
        this.formScoreRecordService = formScoreRecordService;
    }

    public List<FormScoreRecordEntity> loadFormScoreRecordsByWeekAndPresenter(String week, String presenterId) {
        List<FormSubmissionEntity> formSubmissions = formSubmissionService.findAllSubmissionsByWeek(week);
        List<FormScoreRecordEntity> formScoreRecords = new ArrayList<>();

        for(FormSubmissionEntity formSubmission : formSubmissions) {
            Long formId = formSubmission.getId();
            FormScoreRecordEntity record = formScoreRecordService.findByFormIdAndPresenterId(formId, presenterId);
            formScoreRecords.add(record);
        }

        return formScoreRecords;
    }

    public Map<String, List<FormScoreRecordEntity>> loadFormScoreRecordsByWeek(String week) {
        List<FormSubmissionEntity> submissions = formSubmissionService.findAllSubmissionsByWeek(week);

        List<Long> formIds = submissions.stream()
                .map(FormSubmissionEntity::getId)
                .collect(Collectors.toList());

        if (formIds.isEmpty()) {
            return new HashMap<>(); // 該週沒資料就回空
        }

        List<FormScoreRecordEntity> allRecords = formScoreRecordService.findByFormIdIn(formIds);

        return allRecords.stream()
                .collect(Collectors.groupingBy(FormScoreRecordEntity::getPresenterId));
    }


    public void process(FormSubmission formSubmission) {
        saveFormSubmission(formSubmission);

        String submitterId = formSubmission.getSubmitterId();
        String week = formSubmission.getWeek();
        Long formId = formSubmissionService.getFormId(submitterId, week);

        saveFormScoreRecords(formSubmission, formId);
    }

    private void saveFormSubmission(FormSubmission formSubmission) {
        FormSubmissionEntity formSubmissionEntity = FormSubmissionEntity.builder()
                .submitterId(formSubmission.getSubmitterId())
                .week(formSubmission.getWeek())
                .submitDateTime(formSubmission.getSubmitDateTime())
                .comment(formSubmission.getComment())
                .build();

        formSubmissionService.save(formSubmissionEntity);
    }

    private void saveFormScoreRecords(FormSubmission formSubmission, Long formId) {
        String reviewerId = formSubmission.getSubmitterId();

        List<FormScoreRecordEntity> formScoreRecords = new ArrayList<>();

        for(FormScoreRecord scoreRecord : formSubmission.getScores()) {
            String score = scoreRecord.getScore();
            String presenterId = scoreRecord.getPresenterId();

            FormScoreRecordEntity formScoreRecordEntity = FormScoreRecordEntity.builder()
                    .formId(formId)
                    .score(score)
                    .presenterId(presenterId)
                    .reviewerId(reviewerId)
                    .build();

            formScoreRecords.add(formScoreRecordEntity);
        }

        formScoreRecordService.saveAll(formScoreRecords);
    }
}
