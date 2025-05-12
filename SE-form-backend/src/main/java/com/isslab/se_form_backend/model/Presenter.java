package com.isslab.se_form_backend.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class Presenter {

    private Student presenter;
    private String presentWeek;
    private int presentOrder;
    private PresenterGradeSummary grade;
}
