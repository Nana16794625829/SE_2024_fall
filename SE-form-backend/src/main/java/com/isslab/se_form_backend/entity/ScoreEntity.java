package com.isslab.se_form_backend.entity;

import java.util.Date;

public class ScoreEntity {

    private Long id;

    private Date reviewDate;
    private String presenterId;
    private int presentOrder;
    private String reviewerId;
    private String score;
    private int grade;
    private int standardDeviation;
    private int zScore;
    private int presenterGrade;
    private int reviewerGrade;
    private boolean outlier;
    private int round;
}
