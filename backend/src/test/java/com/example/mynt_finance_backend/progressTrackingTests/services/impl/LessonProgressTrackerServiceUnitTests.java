package com.example.mynt_finance_backend.progressTrackingTests.services.impl;

import com.example.mynt_finance_backend.progressTracking.domain.entities.LessonProgressTracker;
import com.example.mynt_finance_backend.util.progressTrackingUtils.MasteryAndCompletenessUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LessonProgressTrackerServiceUnitTests {

    @Test
    public void testInitialCorrectAnswer() {

        LessonProgressTracker lpt = new LessonProgressTracker();
        lpt.setMastery(0.0);

        MasteryAndCompletenessUtil.updateMastery(lpt, true);

        Assertions.assertTrue(lpt.getMastery() > 0);
    }

    @Test
    public void testInitialIncorrectAnswer() {

        LessonProgressTracker lpt = new LessonProgressTracker();
        lpt.setMastery(0.0);

        MasteryAndCompletenessUtil.updateMastery(lpt, false);

        Assertions.assertTrue(lpt.getMastery() >= 0);
        Assertions.assertEquals(0, lpt.getMastery());
    }

    @Test
    public void testCorrectAnswerIncreasesMastery() {

        LessonProgressTracker lpt = new LessonProgressTracker();
        lpt.setMastery(0.5);

        MasteryAndCompletenessUtil.updateMastery(lpt, true);

        Assertions.assertTrue(lpt.getMastery() > 0.5);
    }

    @Test
    public void testIncorrectAnswerDecreasesMastery() {

        LessonProgressTracker lpt = new LessonProgressTracker();
        lpt.setMastery(0.5);

        MasteryAndCompletenessUtil.updateMastery(lpt, false);

        Assertions.assertTrue(lpt.getMastery() < 0.5);
    }

    @Test
    public void testMultipleCorrectConvergeToOne() {

        LessonProgressTracker lpt = new LessonProgressTracker();
        lpt.setMastery(0.5);

        for (int i = 0; i < 20; i++) {
            MasteryAndCompletenessUtil.updateMastery(lpt, true);
        }

        Assertions.assertTrue(lpt.getMastery() > 0.5);
        Assertions.assertTrue(lpt.getMastery() <= 1.0);
    }

    @Test
    public void testMultipleIncorrectConvergeToZero() {

        LessonProgressTracker lpt = new LessonProgressTracker();
        lpt.setMastery(0.5);

        for (int i = 0; i < 20; i++) {
            MasteryAndCompletenessUtil.updateMastery(lpt, false);
        }

        Assertions.assertTrue(lpt.getMastery() < 0.5);
        Assertions.assertTrue(lpt.getMastery() >= 0.0);
    }

    @Test
    public void testRecencyWeightedHigher() {

        LessonProgressTracker lpt = new LessonProgressTracker();
        lpt.setMastery(0.0);

        for (int i = 0; i < 10; i++) {
            MasteryAndCompletenessUtil.updateMastery(lpt, true);
        }

        double masterBeforeWrongAnswer = lpt.getMastery();
        MasteryAndCompletenessUtil.updateMastery(lpt, false);
        double masterAfterWrongAnswer = lpt.getMastery();

        // Mastery should drop after wrong answer regardless of long previous success streak
        Assertions.assertTrue(masterBeforeWrongAnswer > masterAfterWrongAnswer);
    }

    @Test
    public void testCalculateInitialCompleteness() {

        int numberOfCoreQuestions = 10;
        double currentCompleteness = 0.0;

        Assertions.assertEquals(
                MasteryAndCompletenessUtil.calculateNewCompleteness(numberOfCoreQuestions, currentCompleteness),
                0.1
        );
    }

    @Test
    public void testCalculateCompleteness() {

        int numberOfCoreQuestions = 100;
        double currentCompleteness = 0.7;

        Assertions.assertEquals(
                MasteryAndCompletenessUtil.calculateNewCompleteness(numberOfCoreQuestions, currentCompleteness),
                0.71
        );
    }
}
