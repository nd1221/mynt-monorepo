package com.example.mynt_finance_backend.learningPlatform.domain.DTOs;

import java.util.Optional;
import java.util.Set;

public record SectionDTO(
        Integer id,
        String title,
        String description,
        Integer position,
        Optional<Integer> courseId,
        Set<Integer> lessonIds,
        Optional<Integer> testId,
        int duration
) {
}
