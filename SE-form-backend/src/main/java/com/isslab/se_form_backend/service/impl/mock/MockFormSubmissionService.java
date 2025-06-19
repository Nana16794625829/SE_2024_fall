package com.isslab.se_form_backend.service.impl.mock;

import com.isslab.se_form_backend.entity.FormSubmissionEntity;
import com.isslab.se_form_backend.helper.MockFormSubmission;
import com.isslab.se_form_backend.service.AbstractFormSubmissionService;

import java.util.List;

public class MockFormSubmissionService extends AbstractFormSubmissionService {

    @Override
    public void save(FormSubmissionEntity formSubmissionEntity) {
    }

    @Override
    public List<FormSubmissionEntity> fetchAllSubmissionsByWeek(String week) {
        return MockFormSubmission.loadFromCsv("/mock/sample.csv");
    }

}
