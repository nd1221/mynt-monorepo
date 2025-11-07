package com.example.mynt_finance_backend.progressTracking.domain.dtos;

public record TestProgressUpdateDTO(
        int sectionId,
        int score,
        int questionsAttempted,
        boolean outOfTime,
        long testTimeMillis
) {
}
