package com.example.mynt_finance_backend.progressTracking.domain.dtos;

import java.time.LocalDate;

// Used to return lightweight data for populating searchbar in frontend
public record CourseTrackerSearchSummaryDTO(
        long id,
        String courseTitle,
        LocalDate lastReviewDate,
        double mastery,
        double completeness,
        String iconURL
) {
}
