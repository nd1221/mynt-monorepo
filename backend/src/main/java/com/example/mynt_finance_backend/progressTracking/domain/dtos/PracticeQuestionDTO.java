package com.example.mynt_finance_backend.progressTracking.domain.dtos;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.QuestionDTO;

// Returns question DTO and question progress DTO in convenient form for frontend practice feature to receive
public record PracticeQuestionDTO(
        QuestionDTO question,
        long questionProgressTrackerId
) {
}
