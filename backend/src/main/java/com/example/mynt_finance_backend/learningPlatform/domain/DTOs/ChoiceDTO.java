package com.example.mynt_finance_backend.learningPlatform.domain.DTOs;

public record ChoiceDTO(
        Integer id,
        String choiceText,
        Boolean isCorrect
) {
}
