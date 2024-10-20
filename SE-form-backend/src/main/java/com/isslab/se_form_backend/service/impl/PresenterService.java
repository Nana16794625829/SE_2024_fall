package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.model.Presenter;

import java.util.List;

public class PresenterService {

    public List<Presenter> getPresenters() {
        Presenter presenter1 = Presenter
                .builder()
                .index(1)
                .name("Peter")
                .studentId("0000001")
                .build();

        Presenter presenter2 = Presenter
                .builder()
                .index(2)
                .name("Ruby")
                .studentId("0000002")
                .build();

        return List.of(presenter1, presenter2);
    }
}
