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
public class StudentGradeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentId;  // 學生學號

    @ElementCollection
    @CollectionTable(
            name = "grade_details",
            joinColumns = @JoinColumn(name = "student_grade_id")
    )
    @Column(name = "grade")
    private List<Double> grades = new ArrayList<>();  // 該週的詳細成績列表

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weekly_grade_id")
    private WeeklyGradeEntity weeklyGrade;
}
