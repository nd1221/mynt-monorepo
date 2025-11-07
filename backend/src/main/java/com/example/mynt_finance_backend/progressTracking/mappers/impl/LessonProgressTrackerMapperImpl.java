package com.example.mynt_finance_backend.progressTracking.mappers.impl;

import com.example.mynt_finance_backend.progressTracking.domain.dtos.LessonProgressTrackerDTO;
import com.example.mynt_finance_backend.progressTracking.domain.entities.LessonProgressTracker;
import com.example.mynt_finance_backend.progressTracking.domain.entities.QuestionProgressTracker;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import org.springframework.stereotype.Component;

@Component
public class LessonProgressTrackerMapperImpl implements Mapper<LessonProgressTrackerDTO, LessonProgressTracker> {

    @Override
    public LessonProgressTrackerDTO mapToDTO(LessonProgressTracker lpt) {
        return new LessonProgressTrackerDTO(
                lpt.getId(),
                lpt.getLesson().getId(),
                getChildEntitySet(lpt.getQuestionProgressTrackers(), QuestionProgressTracker::getId),
                lpt.getCourseProgressTracker().getId(),
                lpt.getStreak(),
                lpt.getTotalQuestionsReviewed(),
                lpt.getAccuracy(),
                lpt.getAverageQuestionTime().toMillis(),
                lpt.getQuestionsDueToday(),
                lpt.getFirstPracticeDate() == null ? null : lpt.getFirstPracticeDate().toString(),
                lpt.getLastPracticeDate() == null ? null : lpt.getLastPracticeDate().toString(),
                lpt.getCompleteness(),
                lpt.getMastery()
        );
    }

    @Override
    public LessonProgressTracker mapToEntity(LessonProgressTrackerDTO dto) {
        return new LessonProgressTracker();
    }
}
