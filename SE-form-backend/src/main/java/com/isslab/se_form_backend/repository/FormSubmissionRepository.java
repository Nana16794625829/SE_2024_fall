package com.isslab.se_form_backend.repository;

import com.isslab.se_form_backend.entity.FormSubmissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormSubmissionRepository extends JpaRepository<FormSubmissionEntity, Long> {
}
