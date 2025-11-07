package com.example.mynt_finance_backend.learningPlatform.domain.DTOs;

import java.util.Set;
import java.util.Optional;

public record TestDTO(
        Integer id,
        Optional<Integer> sectionId,
        Integer numberOfQuestions,
        Long timeLimit,
        Set<Long> questionIds
) {
}
