package com.isslab.se_form_backend.repository;

import com.isslab.se_form_backend.entity.PresenterGradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PresenterRepository extends JpaRepository<PresenterGradeEntity, Long> {
    void deleteByPresenterIdAndWeek(String presenterId, String week);

    @Query("SELECT r.grade FROM PresenterGradeEntity r WHERE r.presenterId = :presenterId AND r.week = :week")
    List<Double> getGradesByPresenterIdAndWeek(@Param("presenterId") String presenterId,
                                              @Param("week") String week);

    PresenterGradeEntity getByPresenterIdAndWeek(String presenterId, String week);
    PresenterGradeEntity getByWeekAndPresentOrder(String week, int order);
    List<PresenterGradeEntity> findAllByPresenterIdInAndWeekIn(List<String> presenterId, List<String> week);
}
