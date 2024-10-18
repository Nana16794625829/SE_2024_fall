package com.isslab.se_form_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GradeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
