package com.example.mynt_finance_backend.progressTracking.domain.dtos;

import java.time.LocalDate;

public record LessonTrackerSearchSummaryDTO(
        long id,
        int lessonId,
        String lessonTitle,
        String sectionTitle,
        int sectionPosition,
        int lessonPosition,
        LocalDate lastReviewDate,
        double mastery,
        double completeness,
        String iconURL
) {
}
