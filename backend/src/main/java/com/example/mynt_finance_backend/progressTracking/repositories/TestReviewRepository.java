package com.example.mynt_finance_backend.progressTracking.repositories;

import com.example.mynt_finance_backend.progressTracking.domain.entities.TestReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TestReviewRepository extends JpaRepository<TestReview, Long> {

    @Query("""
                SELECT review
                FROM TestReview review
                WHERE review.testProgressTracker.id = :testProgressTrackerId
                AND review.dateAttempted >= :startDate
                ORDER BY review.dateAttempted
            """)
    public List<TestReview> findAllFromDate(
            @Param("testProgressTrackerId") long testProgressTrackerId,
            @Param("startDate") LocalDate startDate
    );
}