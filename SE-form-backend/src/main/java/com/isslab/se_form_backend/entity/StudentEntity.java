package com.isslab.se_form_backend.entity;

import com.isslab.se_form_backend.model.ClassType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class StudentEntity {
    @Id
    private String studentId;

    private String name;
    private String email;
    private ClassType classType;
}
