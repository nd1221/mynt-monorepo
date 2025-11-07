package com.example.mynt_finance_backend.progressTracking.services.utils;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.QuestionEntity;
import com.example.mynt_finance_backend.progressTracking.controllers.utils.QuestionBankSearchParam;
import com.example.mynt_finance_backend.progressTracking.controllers.utils.ReviewSessionSearchParam;
import com.example.mynt_finance_backend.progressTracking.domain.entities.AggregateQuestionTracker;
import com.example.mynt_finance_backend.progressTracking.domain.entities.QuestionProgressTracker;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

import static com.example.mynt_finance_backend.util.progressTrackingUtils.Difficulty.*;

public class SpecificationUtil {

    public static double difficultyLowerBound(int difficulty) {
        return switch (difficulty) {
            case 1 -> EASY.getThreshold();
            case 2 -> MEDIUM.getThreshold();
            default -> HARD.getThreshold();
        };
    }

    public static double difficultyUpperBound(int difficulty) {
        return switch (difficulty) {
            case 1 -> UPPER_BOUND.getThreshold();
            case 2 -> EASY.getThreshold();
            default -> MEDIUM.getThreshold();
        };
    }

    public static double difficultyLowerBound(String difficulty) {
        return switch (difficulty) {
            case "easy" -> EASY.getThreshold();
            case "medium" -> MEDIUM.getThreshold();
            default -> HARD.getThreshold();
        };
    }

    public static double difficultyUpperBound(String difficulty) {
        return switch (difficulty) {
            case "easy" -> UPPER_BOUND.getThreshold();
            case "medium" -> EASY.getThreshold();
            default -> MEDIUM.getThreshold();
        };
    }
}
