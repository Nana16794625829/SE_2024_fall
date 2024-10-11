package com.isslab.se_form_backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class ScoreEntity {

    private Long id;

    private Date reviewDate;
    private String presenterId;
    private int presentOrder;
    private String reviewerId;
    private String score;
    private int grade;
    private double standardDeviation;
    private double zScore;
    private double reviewerGrade;
    private double presenterGrade;
    private boolean outlier;
    private int round;
}
