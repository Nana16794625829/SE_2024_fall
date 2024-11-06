package com.isslab.se_form_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewerEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    private String week;
    private String reviewerId;

    @ElementCollection
    @CollectionTable(
            name = "reviewer_grades",
            joinColumns = @JoinColumn(name = "reviewer_id")
    )
    @Column(name = "grade")
    private List<Double> gradeList = new ArrayList<>();
}
