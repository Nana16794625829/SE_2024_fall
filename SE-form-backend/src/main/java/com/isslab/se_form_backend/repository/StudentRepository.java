package com.isslab.se_form_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentsEntity, String> {
}
