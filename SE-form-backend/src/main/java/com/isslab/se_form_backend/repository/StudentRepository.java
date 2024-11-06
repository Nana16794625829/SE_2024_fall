package com.isslab.se_form_backend.repository;

import com.isslab.se_form_backend.entity.StudentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentsEntity, String> {
}
