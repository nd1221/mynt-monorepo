package com.example.mynt_finance_backend.progressTracking.domain.dtos;

public record LessonMasteryCompletenessDTO(
        int lessonId,
        String lessonTitle,
        int lessonPosition,
        boolean lessonProgressTrackerExists,
        Long lessonProgressTrackerId,
        double completeness,
        double mastery
) {
}
