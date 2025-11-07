package com.example.mynt_finance_backend.progressTracking.domain.dtos;

public record QuestionReviewDTO(
        long questionProgressTrackerId,
        String reviewDate,
        long questionTimeMillis,
        boolean correct,
        int userRating
) {
}
