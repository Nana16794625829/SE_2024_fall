package com.isslab.se_form_backend.repository;

import com.isslab.se_form_backend.entity.StudentEntity;
import com.isslab.se_form_backend.model.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    Student getStudentByStudentId(String studentId);
    @Transactional
    void deleteByStudentId(String studentId);
}
