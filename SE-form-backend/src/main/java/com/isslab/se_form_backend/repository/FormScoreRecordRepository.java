package com.isslab.se_form_backend.repository;

import com.isslab.se_form_backend.entity.FormScoreRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormScoreRecordRepository extends JpaRepository<FormScoreRecordEntity, Long> {
}
