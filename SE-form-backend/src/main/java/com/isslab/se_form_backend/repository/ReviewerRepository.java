package com.isslab.se_form_backend.repository;

import com.isslab.se_form_backend.entity.ReviewerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewerRepository extends JpaRepository<ReviewerEntity, Long> {
    void deleteByReviewerIdAndWeek(String reviewerId, String week);
}
