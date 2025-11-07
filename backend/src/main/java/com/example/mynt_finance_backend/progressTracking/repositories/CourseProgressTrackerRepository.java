package com.example.mynt_finance_backend.progressTracking.repositories;

import com.example.mynt_finance_backend.progressTracking.domain.entities.CourseProgressTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CourseProgressTrackerRepository extends JpaRepository<CourseProgressTracker, Long> {

    @Query("""
            SELECT COUNT (cpt)
            FROM CourseProgressTracker cpt
            WHERE cpt.userProgressTracker.id = :userProgressTrackerId
            AND cpt.course.id = :courseId
            """)
    long existsByUserProgressTrackerAndCourse(
            @Param("userProgressTrackerId") Long userProgressTrackerId,
            @Param("courseId") Integer courseId
    );

    @Query("""
            SELECT cpt
            FROM CourseProgressTracker cpt
            WHERE cpt.userProgressTracker.id = :userProgressTrackerId
            AND cpt.course.id = :courseId
            """)
    CourseProgressTracker findByUserProgressTrackerAndCourse(
            @Param("userProgressTrackerId") Long userProgressTrackerId,
            @Param("courseId") Integer courseId
    );

    @Query("""
            SELECT SUM (lpt.questionsDueToday) FROM LessonProgressTracker lpt
            """)
    int getQuestionsDueToday(@Param("courseProgressTrackerId") long courseProgressTrackerId);

    @Query("""
            SELECT COUNT(lessons)
            FROM CourseProgressTracker cpt
            JOIN cpt.course.sections sections
            JOIN sections.lessons lessons
            WHERE cpt.id = :cptId
            """)
    int getTotalLessonsById(@Param("cptId") long cptId);

    @Query("""
            SELECT AVG(lpts.mastery)
            FROM CourseProgressTracker cpt
            JOIN cpt.lessonProgressTrackers lpts
            WHERE cpt.id = :cptId
            """)
    Double getAverageMastery(@Param("cptId") long cptId);

    @Query("""
            SELECT AVG(lpts.completeness)
            FROM CourseProgressTracker cpt
            JOIN cpt.lessonProgressTrackers lpts
            WHERE cpt.id = :cptId
            """)
    Double getAverageCompleteness(@Param("cptId") long cptId);

    @Query("""
            SELECT Count(qpts)
            FROM CourseProgressTracker cpt
            JOIN cpt.lessonProgressTrackers lpts
            JOIN lpts.questionProgressTrackers qpts
            WHERE cpt.id = :cptId
            AND qpts.nextReviewDate = :today
            """)
    Long numberOfReviewsDueToday(
            @Param("cptId") long cptId,
            @Param("today") LocalDate today
    );

    // Deletes all inactive course trackers with a deactivation date one year prior to today
    @Modifying
    @Query("""
            DELETE
            FROM CourseProgressTracker cpt
            WHERE cpt.deactivationDate < :cutoff
            """)
    void deleteInactiveCourseTrackers(
            @Param("cutoff") LocalDate cutoff
    );
}
