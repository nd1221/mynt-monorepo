package com.example.mynt_finance_backend.progressTracking.services.impl;

import com.example.mynt_finance_backend.progressTracking.domain.entities.QuestionProgressTracker;
import com.example.mynt_finance_backend.progressTracking.services.SpacedRepetitionService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SpacedRepetitionServiceImpl implements SpacedRepetitionService {

    private final double INITIAL_EASE_FACTOR = 1.3; // Taken from Wozniak 1998
    private final int MAX_USER_RATING = 5;

    // SM-2 spaced repetition algorithm
    @Override
    public void runAlgorithm(QuestionProgressTracker qpt) {

        if (qpt.getUserRating() < 3) {
            qpt.resetRepetitions();
            qpt.setInterval(1);
        } else {
            updateInterval(qpt, qpt.getRepetitions());
            qpt.incrementRepetitions();
            double newEasinessFactor = calculateEasinessFactor(qpt.getEasinessFactor(), qpt.getUserRating());
            qpt.setEasinessFactor(Math.max(newEasinessFactor, INITIAL_EASE_FACTOR));
        }
    }

    private void updateInterval(QuestionProgressTracker qpt, int repetitions) {

        if (repetitions == 0) {
            qpt.setInterval(1);
        } else if (repetitions == 1) {
            qpt.setInterval(6);
        } else {
            double nextInterval = qpt.getInterval() * qpt.getEasinessFactor();
            qpt.setInterval((int) Math.ceil(nextInterval));
        }
    }

    private double calculateEasinessFactor(double prevEasinessFactor, int userRating) {
        // All constants taken from Wozniak 1998
        return prevEasinessFactor + (0.1 - (MAX_USER_RATING - userRating) * (0.08 + (MAX_USER_RATING - userRating) * 0.02));
    }
}
