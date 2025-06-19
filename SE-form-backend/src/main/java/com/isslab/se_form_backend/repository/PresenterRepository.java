package com.isslab.se_form_backend.repository;

import com.isslab.se_form_backend.entity.PresenterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PresenterRepository extends JpaRepository<PresenterEntity, Long> {
    void deleteByPresenterIdAndWeek(String presenterId, String week);
}
