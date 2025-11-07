package com.example.mynt_finance_backend.progressTracking.repositories;

import com.example.mynt_finance_backend.progressTracking.domain.entities.TestProgressTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestProgressTrackerRepository extends JpaRepository<TestProgressTracker, Long> {

    @Query("""
            SELECT tpt
            FROM TestProgressTracker tpt
            WHERE tpt.courseProgressTracker.id = :courseProgressTrackerId
            AND tpt.test.id = :testId
            """)
    Optional<TestProgressTracker> readByCourseProgressTrackerAndTest(
            @Param("courseProgressTrackerId") long courseProgressTrackerId,
            @Param("testId") int testId
    );
}
