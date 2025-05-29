package com.isslab.se_form_backend.entity;

import com.isslab.se_form_backend.entity.id.PresenterEntityId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "presenter",
        uniqueConstraints = @UniqueConstraint(columnNames = {"presenterId", "week"})
)
public class PresenterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String presenterId;
    private String week;
    private int presentOrder;
    private double grade;
}
