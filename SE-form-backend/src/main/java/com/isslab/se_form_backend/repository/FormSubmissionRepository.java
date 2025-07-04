package com.isslab.se_form_backend.repository;

import com.isslab.se_form_backend.entity.FormSubmissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormSubmissionRepository extends JpaRepository<FormSubmissionEntity, Long> {
    List<FormSubmissionEntity> getAllByWeek(String week);
}
