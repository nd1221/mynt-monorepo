package com.example.mynt_finance_backend.learningPlatform.domain.DTOs;

import com.example.mynt_finance_backend.learningPlatform.domain.entityEnums.ContentDifficulty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public record CourseDTO(
        Integer id,
        String title,
        String description,
        ContentDifficulty difficulty,
        Set<Integer> sectionIds,
        Set<String> tags,
        Set<String> creators,
        Integer numberOfEnrolledUsers,
        String iconURL,
        LocalDateTime createdAt,
        LocalDateTime lastUpdatedAt,
        List<String> objectives,
        List<String> requirements,
        int totalDuration
) {
}
