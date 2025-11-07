package com.example.mynt_finance_backend.progressTracking.domain.dtos;

public record TestProgressTrackerDTO(
        long id,
        int sectionId,
        double averagePercentage,
        long averageQuestionTimeMillis,
        int outOfTimeCount,
        double averagePercentQuestionsAttempted,
        String firstAttempted,
        String lastAttempted,
        int numberOfAttempts
) {
}
