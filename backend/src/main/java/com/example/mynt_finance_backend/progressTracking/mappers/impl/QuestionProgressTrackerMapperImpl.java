package com.example.mynt_finance_backend.progressTracking.mappers.impl;

import com.example.mynt_finance_backend.progressTracking.domain.dtos.QuestionProgressTrackerDTO;
import com.example.mynt_finance_backend.progressTracking.domain.entities.QuestionProgressTracker;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import org.springframework.stereotype.Component;

@Component
public class QuestionProgressTrackerMapperImpl implements Mapper<QuestionProgressTrackerDTO, QuestionProgressTracker> {

    @Override
    public QuestionProgressTrackerDTO mapToDTO(QuestionProgressTracker qpt) {
        return new QuestionProgressTrackerDTO(
                qpt.getId(),
                qpt.getLessonProgressTracker().getId(),
                qpt.getQuestion().getId(),
                qpt.getTotalCount(),
                qpt.getCorrectCount(),
                qpt.getCorrectStreak(),
                qpt.getFirstReviewedDate() == null ? null : qpt.getFirstReviewedDate().toString(),
                qpt.getLastReviewedDate() == null ? null : qpt.getLastReviewedDate().toString(),
                qpt.getNextReviewDate() == null ? null : qpt.getNextReviewDate().toString(),
                qpt.getAverageQuestionTime().toMillis(),
                qpt.getAccuracy()
        );
    }

    @Override
    public QuestionProgressTracker mapToEntity(QuestionProgressTrackerDTO dto) {
        return new QuestionProgressTracker();
    }
}
