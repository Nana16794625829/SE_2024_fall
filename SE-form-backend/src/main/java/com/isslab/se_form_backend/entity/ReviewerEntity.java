package com.isslab.se_form_backend.entity;

import com.isslab.se_form_backend.entity.id.ReviewerEntityId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "reviewer",
        uniqueConstraints = @UniqueConstraint(columnNames = {"reviewerId", "week"})
)
public class ReviewerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reviewerId;
    private String week;
    private double grade;

}
