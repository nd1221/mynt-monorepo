package com.example.mynt_finance_backend.progressTracking.domain.dtos;

public record TestReviewDTO(
        long testProgressTrackerId,
        String reviewDate,
        long testTimeMillis,
        int score,
        int numberOfQuestionsAttempted,
        boolean outOfTime
) {
}
