package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.entity.FormScoreRecordEntity;
import com.isslab.se_form_backend.entity.FormSubmissionEntity;
import com.isslab.se_form_backend.service.AbstractFormScoreRecordService;
import com.isslab.se_form_backend.service.AbstractFormSubmissionService;

import java.util.ArrayList;
import java.util.List;

public class FormProcessingService {

    private final AbstractFormSubmissionService formSubmissionService;
    private final AbstractFormScoreRecordService formScoreRecordService;

    public FormProcessingService(AbstractFormSubmissionService formSubmissionService, AbstractFormScoreRecordService formScoreRecordService) {
        this.formSubmissionService = formSubmissionService;
        this.formScoreRecordService = formScoreRecordService;
    }

    public List<FormScoreRecordEntity> loadFormScoreRecordsByWeekAndPresenter(String week, String presenterId) {
        List<FormSubmissionEntity> formSubmissions = formSubmissionService.fetchAllSubmissionsByWeek(week);
        List<FormScoreRecordEntity> formScoreRecords = new ArrayList<>();

        for(FormSubmissionEntity formSubmission : formSubmissions) {
            Long formId = formSubmission.getId();
            FormScoreRecordEntity record = formScoreRecordService.findByFormIdAndPresenterId(formId, presenterId);
            formScoreRecords.add(record);
        }

        return formScoreRecords;
    }
}
