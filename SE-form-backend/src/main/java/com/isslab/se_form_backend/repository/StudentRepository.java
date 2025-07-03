package com.isslab.se_form_backend.repository;

import com.isslab.se_form_backend.entity.StudentEntity;
import com.isslab.se_form_backend.model.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    Student getStudentByStudentId(String studentId);

    @Query("SELECT s.studentId FROM StudentEntity s")
    Set<String> getAllStudentIds();

    @Transactional
    void deleteByStudentId(String studentId);
}
