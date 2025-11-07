package com.example.mynt_finance_backend.progressTracking.domain.dtos;

public record AggregateQuestionUpdateDTO(
        long questionId,
        boolean correct,
        long questionTimeMillis
) {
}
