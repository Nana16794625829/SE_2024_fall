package com.isslab.se_form_backend.entity;

import com.isslab.se_form_backend.model.ClassType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentsEntity {

    @Id
    private String studentId;

    private String name;
    private String email;
    private ClassType classType;
}
