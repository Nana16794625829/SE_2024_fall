package com.isslab.se_form_backend.repository;

import com.isslab.se_form_backend.entity.FormEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormRepository extends JpaRepository<FormEntity, Long> {
}
