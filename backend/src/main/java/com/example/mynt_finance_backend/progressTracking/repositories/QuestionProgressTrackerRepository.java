package com.example.mynt_finance_backend.progressTracking.repositories;

import com.example.mynt_finance_backend.progressTracking.domain.entities.QuestionProgressTracker;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionProgressTrackerRepository extends JpaRepository<QuestionProgressTracker, Long>, JpaSpecificationExecutor<QuestionProgressTracker> {

    @Query("""
            SELECT qpt FROM QuestionProgressTracker qpt
            WHERE qpt.nextReviewDate = CURRENT_DATE
            AND qpt.lessonProgressTracker.id = :lessonProgressTrackerId
            ORDER BY qpt.nextReviewDate
            """)
    List<QuestionProgressTracker> getQuestionsDueToday(long lessonProgressTrackerId, Pageable pageable);

    @Query("""
            SELECT qpt FROM QuestionProgressTracker qpt
            WHERE qpt.lessonProgressTracker.id = :lessonProgressTrackerId
            ORDER BY qpt.nextReviewDate
            """)
    List<QuestionProgressTracker> getNextReviewQuestions(long lessonProgressTrackerId);

    @Query("""
            SELECT qpt FROM QuestionProgressTracker qpt
            WHERE qpt.lessonProgressTracker.id = :lessonProgressTrackerId
            ORDER BY qpt.nextReviewDate
            """)
    List<QuestionProgressTracker> getNextReviewQuestions(long lessonProgressTrackerId, Pageable pageable);

    @Query("""
            SELECT qpt
            FROM QuestionProgressTracker qpt
            WHERE qpt.question.id = :questionId
            AND qpt.lessonProgressTracker.courseProgressTracker.id = :courseProgressTrackerId
            """)
    Optional<QuestionProgressTracker> findByCourseTrackerAndQuestionId(long questionId, long courseProgressTrackerId);

    @Query("""
            SELECT count(qpt)
            FROM QuestionProgressTracker qpt
            WHERE qpt.lessonProgressTracker.courseProgressTracker.id = :courseProgressTrackerId
            AND qpt.lessonProgressTracker.courseProgressTracker.course.id = :courseId
            """)
    Long findTotalCourseQuestionsAnsweredByCourse(
            @Param("courseProgressTrackerId") long courseProgressTrackerId,
            @Param("courseId") int courseId
    );

    @Query("""
            SELECT count(qpt)
            FROM QuestionProgressTracker qpt
            JOIN qpt.question q
            JOIN AggregateQuestionTracker aqt ON aqt.question = q
            WHERE qpt.lessonProgressTracker.courseProgressTracker.id = :courseProgressTrackerId
                AND qpt.lessonProgressTracker.courseProgressTracker.course.id = :courseId
                AND aqt.accuracy > :lowerThreshold
                AND aqt.accuracy <= :upperThreshold
            """)
    Long findTotalCourseQuestionsAnsweredByCourseTrackerAndDifficulty(
            @Param("courseProgressTrackerId") long courseProgressTrackerId,
            @Param("courseId") int courseId,
            @Param("lowerThreshold") double lowerThreshold,
            @Param("upperThreshold") double upperThreshold
    );

    @Query("""
            SELECT Count(qpt)
            FROM QuestionProgressTracker qpt
            WHERE qpt.question.id = :questionId
            AND qpt.lessonProgressTracker.lesson.id = :lessonId
            AND qpt.lessonProgressTracker.courseProgressTracker.id = :courseTrackerId
            """)
    Long existsByCourseTrackerAndLessonAndQuestion(
            @Param("courseTrackerId") long courseTrackerId,
            @Param("lessonId") int lessonId,
            @Param("questionId") long questionId
    );

    @Query("""
            SELECT qpt
            FROM QuestionProgressTracker qpt
            WHERE qpt.question.id = :questionId
            AND qpt.lessonProgressTracker.lesson.id = :lessonId
            AND qpt.lessonProgressTracker.courseProgressTracker.id = :courseTrackerId
            """)
    QuestionProgressTracker getByCourseTrackerAndLessonAndQuestion(
            @Param("courseTrackerId") long courseTrackerId,
            @Param("lessonId") int lessonId,
            @Param("questionId") long questionId
    );
}
