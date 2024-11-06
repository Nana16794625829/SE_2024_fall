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
public class WeeklyGradeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String week;  // 例如: "week1", "week2" 等

    // 一個週次包含多個學生成績
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "weeklyGrade", orphanRemoval = true)
    private List<StudentGradeEntity> studentGrades = new ArrayList<>();

    // 方便添加學生成績的輔助方法
    public void addStudentGrade(StudentGradeEntity grade) {
        studentGrades.add(grade);
        grade.setWeeklyGrade(this);
    }
}
