package com.example.mynt_finance_backend.progressTracking.domain.dtos;

import java.util.*;

public record LessonProgressTrackerDTO(
        long id,
        int lessonId,
        Set<Long> questionProgressTrackerIds,
        long courseProgressTrackerId,
        int streak,
        int totalQuestionsReviewed,
        double accuracy,
        long averageQuestionTimeMillis,
        int questionsDueToday,
        String firstPracticeDate,
        String lastPracticeDate,
        double completeness,
        double mastery
) {
}
