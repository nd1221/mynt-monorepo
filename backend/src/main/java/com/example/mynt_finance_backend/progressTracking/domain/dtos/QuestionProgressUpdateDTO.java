package com.example.mynt_finance_backend.progressTracking.domain.dtos;

public record QuestionProgressUpdateDTO(
        boolean answered,
        boolean wasCorrect,
        long questionTime
) {
}
