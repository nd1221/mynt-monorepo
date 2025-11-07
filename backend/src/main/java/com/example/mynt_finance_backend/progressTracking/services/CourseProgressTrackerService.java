package com.example.mynt_finance_backend.progressTracking.services;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.CourseDTO;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.*;
import com.example.mynt_finance_backend.progressTracking.domain.entities.CourseProgressTracker;
import com.example.mynt_finance_backend.progressTracking.domain.entities.LessonProgressTracker;
import com.example.mynt_finance_backend.util.baseInterfaces.Service;
import com.example.mynt_finance_backend.util.commonDTOs.EntityTrackerDTO;
import com.example.mynt_finance_backend.util.commonDTOs.TitlePositionDTO;

import java.util.List;
import java.util.Optional;

public interface CourseProgressTrackerService extends Service<Long>, ProgressService {

    boolean existsByUserProgressTrackerAndCourse(Long userProgressTrackerId, Integer courseId);

    Optional<CourseProgressTracker> findById(Long courseProgressTrackerId);

    void activate(Long userProgressTrackerId, Integer courseId);

    void deactivate(Long userProgressTrackerId, Integer courseId);

    CourseProgressTrackerDTO readById(Long courseProgressTrackerId);

    CourseProgressTrackerDTO readByCourseId(long userProgressTrackerId, int courseId);

    void updateCourseProgressTracker(CourseProgressTracker cpt, boolean questionCorrect, long questionTime);

    List<ReviewQuickstartDTO> getAllCourseReviewsDueToday(long userProgressTrackerId);

    List<ReviewQuickstartDTO> getAllLessonReviewsDueTodayByCourse(long courseProgressTrackerId);

    List<SectionReviewQuickstartDTO> getAllLessonReviewsDueTodayBySection(long courseProgressTrackerId);

    List<SectionMasteryCompletenessDTO> getMasteryCompletenessForAllLessons(long courseProgressTrackerId);

    EntityTrackerDTO<CourseDTO, CourseProgressTrackerDTO> getCourseEntityTrackerPair(long courseProgressTrackerId);

    CourseTrackerSearchSummaryDTO getSearchSummary(long courseProgressTrackerId);

    List<LessonTrackerSearchSummaryDTO> getLessonTrackerSearchSummaries(long courseProgressTrackerId);

    List<TitlePositionDTO<Integer>> getSectionTitlesAndPositionsByTrackerId(long courseProgressTrackerId);

    QuestionBankMetadataDTO getQuestionBankMetadataByTrackerId(long courseProgressTrackerId);

    CourseProgressQuestionStatisticsDTO getQuestionStatisticsPerDifficulty(long courseProgressTrackerId);

    boolean isComplete(long courseProgressTrackerId);

    long getReviewsDueToday(long courseProgressTrackerId);

    LessonProgressTracker getLessonTrackerByLessonId(long courseTrackerId, int lessonId);
}
