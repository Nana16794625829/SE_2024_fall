package com.isslab.se_form_backend.repository;

import com.isslab.se_form_backend.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>{
}
