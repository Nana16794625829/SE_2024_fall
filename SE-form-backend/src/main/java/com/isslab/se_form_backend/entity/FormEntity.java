package com.isslab.se_form_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

/**
 * 用來記錄整份表單的填寫資訊，不含 score
 * score 另記錄在 ReviewEntity
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reviewerId;
    private String reviewerName;
    private String reviewerEmail;
    private String comment;
    private Date reviewDate;
    private String week;
}
