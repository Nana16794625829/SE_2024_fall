package com.isslab.se_form_backend.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Statistic {
    double zScore;
    int gradeGap;
    double reviewerGrade;
    Boolean outlier;
}
