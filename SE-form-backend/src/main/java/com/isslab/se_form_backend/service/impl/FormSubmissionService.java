package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.entity.FormSubmissionEntity;
import com.isslab.se_form_backend.model.FormSubmission;
import com.isslab.se_form_backend.repository.FormSubmissionRepository;
import com.isslab.se_form_backend.service.AbstractFormScoreRecordService;
import com.isslab.se_form_backend.service.AbstractFormSubmissionService;

import java.util.List;

public class FormSubmissionService extends AbstractFormSubmissionService {

    private final FormSubmissionRepository formSubmissionRepository;

    public FormSubmissionService(FormSubmissionRepository formSubmissionRepository) {
        this.formSubmissionRepository = formSubmissionRepository;
    }

    @Override
    public void save(FormSubmission formSubmission) {
        saveFormSubmission(formSubmission);
    }

    @Override
    public List<FormSubmissionEntity> fetchAllSubmissionsByWeek(String week) {
        return formSubmissionRepository.getAllByWeek(week);
    }

    private void saveFormSubmission(FormSubmission formSubmission) {
        FormSubmissionEntity formSubmissionEntity = FormSubmissionEntity.builder()
                .submitterId(formSubmission.getSubmitterId())
                .week(formSubmission.getWeek())
                .submitDateTime(formSubmission.getSubmitDateTime())
                .comment(formSubmission.getComment())
                .build();

        formSubmissionRepository.save(formSubmissionEntity);
    }
}
