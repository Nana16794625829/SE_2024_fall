package com.isslab.se_form_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
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
