package com.isslab.se_form_backend.repository;

import com.isslab.se_form_backend.entity.WeeklyGradeEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeeklyGradeRepository extends JpaRepository<WeeklyGradeEntity, Long> {
    @Transactional
    Optional<WeeklyGradeEntity> findByWeek(String week);
}
