package com.isslab.se_form_backend.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class StudentGrade {
    private String studentId;
    private List<Double> grades;
}
