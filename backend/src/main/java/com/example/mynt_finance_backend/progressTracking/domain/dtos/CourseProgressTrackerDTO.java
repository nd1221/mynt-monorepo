package com.example.mynt_finance_backend.progressTracking.domain.dtos;

import java.util.*;

public record CourseProgressTrackerDTO(
        long id,
        long userProgressTrackerId,
        int courseId,
        Map<Integer, Long> lessonProgressTrackerIds,
        Map<Integer, Long> testProgressTrackerIds,
        int streak,
        int totalQuestionsReviewed,
        double accuracy,
        long averageQuestionTimeMillis,
        int questionsDueToday,
        String firstPracticeDate,
        String lastPracticeDate,
        Double mastery,
        Double completeness
) {
}
