package com.isslab.se_form_backend.repository;

import com.isslab.se_form_backend.entity.GradeEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GradeRepository extends JpaRepository<GradeEntity, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE GradeEntity g SET g.presenterGrade = :presenterGrade WHERE g.presenterId = :presenterId")
    void updatePresenterGradeByPresenterId(@Param("presenterGrade") double presenterGrade, @Param("presenterId") String presenterId);

    @Transactional
    @Modifying
    @Query("UPDATE GradeEntity g SET g.standardDeviation = :standardDeviation, g.zScore = :zScore, g.reviewerGrade = :reviewerGrade, g.outlier = :outlier, g.round = :round  WHERE g.reviewerId = :reviewerId AND g.presenterId = :presenterId")
    void updateReviewerDetailByReviewerIdAndPresenterId(
            @Param("reviewerId") String reviewerId,
            @Param("presenterId") String presenterId,
            @Param("standardDeviation") double standardDeviation,
            @Param("zScore") double zScore,
            @Param("reviewerGrade") double reviewerGrade,
            @Param("outlier") Boolean outlier,
            @Param("round") int round
    );

}
