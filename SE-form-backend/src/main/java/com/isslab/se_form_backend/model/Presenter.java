package com.isslab.se_form_backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class Presenter {

    private String presentWeek;
    private String presenterId;
    private int order;
}
