package com.example.mynt_finance_backend.progressTracking.repositories;

import com.example.mynt_finance_backend.progressTracking.domain.entities.QuestionReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface QuestionReviewRepository extends JpaRepository<QuestionReview, Long> {

    @Query("""
                SELECT review
                FROM QuestionReview review
                WHERE review.questionProgressTracker.id = :questionProgressTrackerId
                AND review.reviewDate >= :startDate
                ORDER BY review.reviewDate
            """)
    List<QuestionReview> findAllFromDate(
            @Param("questionProgressTrackerId") long questionProgressTrackerId,
            @Param("startDate") LocalDate startDate
    );

    @Query("""
                SELECT review
                FROM QuestionReview review
                WHERE review.questionProgressTracker.lessonProgressTracker.id = :lessonProgressTrackerId
                AND review.reviewDate >= :startDate
                AND review.questionProgressTracker.aggregateQuestionTracker.accuracy > :lowerThreshold
                AND review.questionProgressTracker.aggregateQuestionTracker.accuracy <= :upperThreshold
                ORDER BY review.reviewDate
            """)
    List<QuestionReview> findAllFromDateByLessonTrackerByDifficulty(
            @Param("lessonProgressTrackerId") long lessonProgressTrackerId,
            @Param("startDate") LocalDate startDate,
            @Param("lowerThreshold") double lowerThreshold,
            @Param("upperThreshold") double upperThreshold
    );

    @Query("""
            SELECT review
            FROM QuestionReview review
            WHERE review.questionProgressTracker.lessonProgressTracker.id = :lessonProgressTrackerId
            ORDER BY review.reviewDate
            """)
    List<QuestionReview> findAllByLessonProgressTracker(@Param("lessonProgressTrackerId") long lessonProgressTrackerId);

    @Query("""
            SELECT review
            FROM QuestionReview review
            WHERE review.questionProgressTracker.lessonProgressTracker.courseProgressTracker.id = :courseProgressTrackerId
            ORDER BY review.reviewDate
            """)
    List<QuestionReview> findAllByCourseProgressTracker(@Param("courseProgressTrackerId") long courseProgressTrackerId);

    @Query("""
            SELECT review.reviewDate, COUNT(review)
            FROM QuestionReview review
            WHERE review.userProgressTracker.id = :userProgressTrackerId
            AND review.reviewDate BETWEEN :startDate AND :endDate
            GROUP BY review.reviewDate
            ORDER BY review.reviewDate
            """)
    List<Object[]> getAggregateQuestionReviewsInDateRange(
            @Param("userProgressTrackerId") long userProgressTrackerId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
