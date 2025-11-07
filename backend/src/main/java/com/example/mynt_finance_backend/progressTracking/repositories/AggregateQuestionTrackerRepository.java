package com.example.mynt_finance_backend.progressTracking.repositories;

import com.example.mynt_finance_backend.progressTracking.domain.entities.AggregateQuestionTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AggregateQuestionTrackerRepository extends JpaRepository<AggregateQuestionTracker, Long> {

    @Query("""
            SELECT Count (aqts)
            FROM AggregateQuestionTracker aqts
            WHERE aqts.question.id = :questionId
            """)
    long existsByQuestionId(@Param("questionId") long questionId);

    @Query("""
            SELECT aqt
            FROM AggregateQuestionTracker aqt
            WHERE aqt.question.id = :questionId
            """)
    Optional<AggregateQuestionTracker> findByQuestionId(@Param("questionId") long questionId);

    @Query("""
            SELECT count (aqt)
            FROM AggregateQuestionTracker aqt
            WHERE aqt.question.lesson.section.course.id = :courseId
            AND aqt.accuracy > :lowerThreshold
            AND aqt.accuracy <= :upperThreshold
            """)
    Long findNumberOfQuestionsInCourseByDifficulty(
            @Param("courseId") long courseId,
            @Param("lowerThreshold") double lowerThreshold,
            @Param("upperThreshold") double upperThreshold
    );
}
