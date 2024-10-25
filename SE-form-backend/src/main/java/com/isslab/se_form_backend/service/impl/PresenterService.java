package com.isslab.se_form_backend.service.impl;

import com.isslab.se_form_backend.entity.PresenterEntity;
import com.isslab.se_form_backend.model.Presenter;
import com.isslab.se_form_backend.repository.PresenterRepository;
import com.isslab.se_form_backend.service.IPresenterService;

import java.util.Date;
import java.util.List;

public class PresenterService implements IPresenterService {

    private final PresenterRepository presenterRepository;

    public PresenterService(PresenterRepository presenterRepository) {
        this.presenterRepository = presenterRepository;
    }

    @Override
    public void savePresenter(Presenter presenter) {
        PresenterEntity presenterEntity = PresenterEntity
                .builder()
                .presenterId(presenter.getPresenterId())
                .presentOrder(presenter.getOrder())
                .week(presenter.getPresentWeek())
                .build();

        presenterRepository.save(presenterEntity);
    }

    @Override
    public String getPresenterIdByWeekAndOrder(String week, int presentOrder) {
        return presenterRepository.findPresenterIdByWeekAndOrder(week, presentOrder);
    }
}
