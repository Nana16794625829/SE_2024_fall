package com.isslab.se_form_backend.entity;

import com.isslab.se_form_backend.entity.id.FormSubmissionEntityId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(FormSubmissionEntityId.class)
public class FormSubmissionEntity {
    @Id
    private String submitterId;
    @Id
    private String week;

    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 提供給 FormScoreRecordEntity 的 fk

    private String submitDateTime;
    private String comment;
}
