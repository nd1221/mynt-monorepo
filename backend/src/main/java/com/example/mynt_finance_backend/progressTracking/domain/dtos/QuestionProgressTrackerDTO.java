package com.example.mynt_finance_backend.progressTracking.domain.dtos;

public record QuestionProgressTrackerDTO(
        long id,
        long lessonProgressTrackerId,
        long questionId,
        int totalCount,
        int correctCount,
        int correctStreak,
        String firstReviewedDate,
        String lastReviewedDate,
        String nextReviewDate,
        long averageQuestionTimeMillis,
        double accuracy
) {
}
