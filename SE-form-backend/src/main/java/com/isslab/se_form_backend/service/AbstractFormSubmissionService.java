package com.isslab.se_form_backend.service;

import com.isslab.se_form_backend.entity.FormSubmissionEntity;
import com.isslab.se_form_backend.model.FormSubmission;

import java.util.List;

public abstract class AbstractFormSubmissionService {

    public abstract void save(FormSubmission formSubmissionEntity);
    public abstract List<FormSubmissionEntity> fetchAllSubmissionsByWeek(String week);

}
