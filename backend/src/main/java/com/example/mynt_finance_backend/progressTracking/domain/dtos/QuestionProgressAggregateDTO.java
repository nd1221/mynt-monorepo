package com.example.mynt_finance_backend.progressTracking.domain.dtos;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.QuestionDTO;

public record QuestionProgressAggregateDTO(
        QuestionDTO question,
        QuestionProgressTrackerDTO qpt,
        AggregateQuestionTrackerDTO aqt
) {
}
