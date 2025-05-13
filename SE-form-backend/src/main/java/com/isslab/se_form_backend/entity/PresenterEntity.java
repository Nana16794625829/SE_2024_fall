package com.isslab.se_form_backend.entity;

import com.isslab.se_form_backend.entity.id.PresenterEntityId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(PresenterEntityId.class)
public class PresenterEntity {
    @Id
    private String presenterId;
    @Id
    private String week;

    private int presentOrder;
    private double grade;
}
