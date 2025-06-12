package com.isslab.se_form_backend.service;

public abstract class AbstractStudentService {

    public abstract boolean isReviewer(String studentId);
    public abstract boolean isPresenter(String studentId);
}
