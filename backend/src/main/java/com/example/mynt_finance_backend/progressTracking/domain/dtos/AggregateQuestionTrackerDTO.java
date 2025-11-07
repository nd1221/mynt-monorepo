package com.example.mynt_finance_backend.progressTracking.domain.dtos;

public record AggregateQuestionTrackerDTO(
        long id,
        long questionId,
        double accuracy,
        long averageTimeMillis,
        int numberOfAttempts
) {
}
