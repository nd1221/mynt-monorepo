package com.example.mynt_finance_backend.util.progressTrackingUtils;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.QuestionEntity;
import com.example.mynt_finance_backend.progressTracking.domain.entities.LessonProgressTracker;

public class QuestionFetchingUtil {

    public static boolean isQuestionLocked(QuestionEntity question, LessonProgressTracker lpt) {
        boolean complete = lpt.getCompleteness() >= 1.0;
        boolean isCore = question.isCore();
        return !complete && !isCore;
    }
}
