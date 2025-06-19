package com.isslab.se_form_backend.entity;

import com.isslab.se_form_backend.entity.id.FormSubmissionEntityId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "form_submission",
        uniqueConstraints = @UniqueConstraint(columnNames = {"submitterId", "week"})
)
public class FormSubmissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    private String submitterId;
    private String week;
    private String submitDateTime;
    private String comment;
}
