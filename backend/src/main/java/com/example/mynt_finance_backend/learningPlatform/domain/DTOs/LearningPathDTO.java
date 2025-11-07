package com.example.mynt_finance_backend.learningPlatform.domain.DTOs;

import com.example.mynt_finance_backend.learningPlatform.domain.entityEnums.ContentDifficulty;

import java.time.LocalDateTime;
import java.util.Set;

public record LearningPathDTO(
        Integer id,
        String title,
        String description,
        ContentDifficulty difficulty,
        Set<Integer> courseIds,
        Set<String> tags,
        Set<String> creators,
        Integer numberOfEnrolledUsers,
        String iconURL,
        LocalDateTime createdAt,
        LocalDateTime lastUpdatedAt
) {
}
