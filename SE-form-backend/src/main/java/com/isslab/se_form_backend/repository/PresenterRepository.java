package com.isslab.se_form_backend.repository;

import com.isslab.se_form_backend.entity.PresenterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PresenterRepository extends JpaRepository<PresenterEntity, Long> {

    @Query("SELECT p.presenterId FROM PresenterEntity p WHERE p.week = :week AND p.presentOrder = :presentOrder")
    String findPresenterIdByWeekAndOrder(@Param("week") String week,
                                         @Param("presentOrder") int presentOrder);

}
