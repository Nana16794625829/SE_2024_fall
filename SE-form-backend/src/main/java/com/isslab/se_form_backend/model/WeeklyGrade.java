package com.isslab.se_form_backend.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class WeeklyGrade {
    private String week;
    private List<StudentGrade> studentGrades;
}
