package com.example.mynt_finance_backend.progressTracking.services;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.LessonDTO;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.*;
import com.example.mynt_finance_backend.progressTracking.domain.entities.LessonProgressTracker;
import com.example.mynt_finance_backend.util.baseInterfaces.Service;

import java.util.List;
import java.util.Map;

public interface LessonProgressTrackerService extends Service<Long>, ProgressService {

    LessonProgressTrackerDTO createLessonProgressTracker(Long courseProgressTrackerId, Integer lessonId);

    LessonProgressTrackerDTO readById(Long lessonProgressTrackerId);

    LessonProgressTracker findLessonProgressTrackerById(long lptId);

    void updateLessonProgressTracker(LessonProgressTracker lpt, long questionId, boolean questionCorrect, long questionTime);

    // Separated from getQuestions() to allow for easier future extension of due question features
    List<PracticeQuestionDTO> getDueQuestions(long lessonProgressTrackerId);

    long getNumberOfQuestionsDueToday(long lptId);

    List<PracticeQuestionDTO> getQuestions(long lessonProgressTrackerId);

    List<PracticeQuestionDTO> getQuestions(long lessonProgressTrackerId, int numberRequested);

    void updateCompleteness(LessonProgressTracker lpt, boolean isCore);

    NavigableEntityTrackerDTO<LessonDTO, LessonProgressTrackerDTO> getLessonOverviewData(long lessonTrackerId);

//    List<CourseLessonMasteryCompletenessDTO> getMasteryCompletenessById(List<Long> lessonProgressTrackerIds);
}
