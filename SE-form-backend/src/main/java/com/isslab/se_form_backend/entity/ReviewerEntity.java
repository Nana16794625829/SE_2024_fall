package com.isslab.se_form_backend.entity;

import com.isslab.se_form_backend.entity.id.ReviewerEntityId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(ReviewerEntityId.class)
public class ReviewerEntity {
    @Id
    private String reviewerId;
    @Id
    private String week;

    private double grade;

}
