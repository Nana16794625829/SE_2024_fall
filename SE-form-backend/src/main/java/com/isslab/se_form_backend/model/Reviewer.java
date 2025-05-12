package com.isslab.se_form_backend.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Reviewer {
    private Student reviewer;
    private String week;
    private double grade;
}
