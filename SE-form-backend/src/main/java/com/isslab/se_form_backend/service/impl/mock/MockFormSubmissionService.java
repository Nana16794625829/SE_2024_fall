package com.isslab.se_form_backend.service.impl.mock;

import com.isslab.se_form_backend.entity.FormSubmissionEntity;
import com.isslab.se_form_backend.helper.MockDataBuilder;
import com.isslab.se_form_backend.model.FormSubmission;
import com.isslab.se_form_backend.service.AbstractFormSubmissionService;

import java.util.List;

public class MockFormSubmissionService extends AbstractFormSubmissionService {

    @Override
    public void save(FormSubmission formSubmissionEntity) {
    }

    @Override
    public List<FormSubmissionEntity> fetchAllSubmissionsByWeek(String week) {
        return MockDataBuilder.loadSubmissionFromCsv("/mock/sample.csv");
    }

}
