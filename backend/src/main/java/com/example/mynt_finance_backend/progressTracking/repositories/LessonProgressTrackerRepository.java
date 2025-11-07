package com.example.mynt_finance_backend.progressTracking.repositories;

import com.example.mynt_finance_backend.progressTracking.domain.entities.LessonProgressTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface LessonProgressTrackerRepository extends JpaRepository<LessonProgressTracker, Long> {

    @Query("""
            SELECT COUNT (qpt)
            FROM QuestionProgressTracker qpt
            WHERE qpt.lessonProgressTracker.id = :lessonProgressTrackerId
            AND qpt.nextReviewDate = CURRENT_DATE
            """)
    int getQuestionsDueToday(@Param("lessonProgressTrackerId") long lessonProgressTrackerId);

    @Query("""
            SELECT COUNT (lpt)
            FROM LessonProgressTracker lpt
            WHERE lpt.lesson.id = :lessonId
            AND lpt.courseProgressTracker.id = :cptId
            """)
    long existsByLessonAndCourseProgressTracker(@Param("lessonId") int lessonId, @Param("cptId") long cptId);

    @Query("""
            SELECT lpt
            FROM LessonProgressTracker lpt
            WHERE lpt.lesson.id = :lessonId
            AND lpt.courseProgressTracker.id = :cptId
            """)
    LessonProgressTracker findByLessonAndCourseProgressTracker(@Param("lessonId") int lessonId, @Param("cptId") long cptId);

    @Query("""
            SELECT COUNT(qpts)
            FROM LessonProgressTracker lpt
            JOIN lpt.questionProgressTrackers qpts
            WHERE lpt.id = :lptId
            AND qpts.nextReviewDate = :today
            """)
    long getNumberOfQuestionsDueToday(@Param("lptId") long lptId, @Param("today") LocalDate today);
}
