package com.example.mynt_finance_backend.progressTracking.domain.dtos;

public record CourseProgressQuestionStatisticsDTO(
        Long total,
        Long totalCompleted,
        Long totalEasy,
        Long easyCompleted,
        Long totalMedium,
        Long mediumCompleted,
        Long totalHard,
        Long hardCompleted
) {
}
