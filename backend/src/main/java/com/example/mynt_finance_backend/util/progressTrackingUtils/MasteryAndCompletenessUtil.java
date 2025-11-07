package com.example.mynt_finance_backend.util.progressTrackingUtils;

import com.example.mynt_finance_backend.progressTracking.domain.entities.LessonProgressTracker;

public final class MasteryAndCompletenessUtil {

    private static final double MASTERY_DECAY_PARAM = 0.90;

    public static double calculateNewCompleteness(int numberOfCoreQuestions, double currentCompleteness) {
        if (numberOfCoreQuestions <= 0) {
            return currentCompleteness;
        }
        int numberCompleted = (int) Math.round(currentCompleteness * numberOfCoreQuestions);
        if (numberCompleted < numberOfCoreQuestions) {
            numberCompleted++;
        }
        return (double) numberCompleted / numberOfCoreQuestions;
    }

    public static void updateMastery(LessonProgressTracker lpt, boolean correct) {
        double oldMastery = lpt.getMastery();
        int correctSignal = correct ? 1 : 0;
        double newMastery = MASTERY_DECAY_PARAM * oldMastery + (1 - MASTERY_DECAY_PARAM) * correctSignal;
        lpt.setMastery(newMastery);
    }
}
