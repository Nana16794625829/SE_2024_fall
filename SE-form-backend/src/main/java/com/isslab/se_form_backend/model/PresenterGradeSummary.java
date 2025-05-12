package com.isslab.se_form_backend.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PresenterGradeSummary {
    double avgGrade;
    double stdDev;
    double zScore;
    int gradeGap;
    Boolean outlier;
    int round;
}
