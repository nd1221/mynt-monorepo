package com.example.mynt_finance_backend.progressTracking.services.utils;

import com.example.mynt_finance_backend.progressTracking.controllers.utils.ReviewSessionSearchParam;
import com.example.mynt_finance_backend.progressTracking.domain.entities.AggregateQuestionTracker;
import com.example.mynt_finance_backend.progressTracking.domain.entities.QuestionProgressTracker;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReviewSessionSpecification {

    public static Specification<QuestionProgressTracker> applyReviewSessionFilters(ReviewSessionSearchParam params, long courseProgressTrackerId) {

        return (root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();

            // Necessary joins
            // AggregateQuestionTracker
            Join<QuestionProgressTracker, AggregateQuestionTracker> aqtJoin = root.join("aggregateQuestionTracker", JoinType.LEFT);

            // Filters
            applyCourseTrackerPredicate(predicates, root, builder, courseProgressTrackerId);
            applySectionAndLessonPredicate(predicates, root, builder, params);
            applyDifficultyPredicate(predicates, root, builder, params, aqtJoin);
            applyNextReviewDatePredicate(predicates, root, builder);

            // Sort
            // NO PARTICULAR ORDER

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void applyCourseTrackerPredicate(
            List<Predicate> predicates,
            Root<QuestionProgressTracker> root,
            CriteriaBuilder builder,
            long courseProgressTrackerId
    ) {
        predicates.add(builder.equal(
                root.get("lessonProgressTracker").get("courseProgressTracker").get("id"), courseProgressTrackerId)
        );
    }

    private static void applySectionAndLessonPredicate(
            List<Predicate> predicates,
            Root<QuestionProgressTracker> root,
            CriteriaBuilder builder,
            ReviewSessionSearchParam params
    ) {
        if (params.section() != null && params.section() != 0) {
            // lesson predicate
            if (params.lesson() != null && params.lesson() != 0) {
                predicates.add(builder.equal(root.get("question").get("lesson").get("id"), params.lesson()));
            } else {
                predicates.add(builder.equal(root.get("question").get("lesson").get("section").get("id"), params.section()));
            }
        }
    }

    private static void applyDifficultyPredicate(
            List<Predicate> predicates,
            Root<QuestionProgressTracker> root,
            CriteriaBuilder builder,
            ReviewSessionSearchParam params,
            Join<QuestionProgressTracker, AggregateQuestionTracker> join
    ) {
        if (params.difficulty() != null && params.difficulty() != 0) {
            predicates.add(
                    builder.between(
                            join.get("accuracy"),
                            SpecificationUtil.difficultyLowerBound(params.difficulty()),
                            SpecificationUtil.difficultyUpperBound(params.difficulty())
                    )
            );
        }
    }

    private static void applyNextReviewDatePredicate(
            List<Predicate> predicates,
            Root<QuestionProgressTracker> root,
            CriteriaBuilder builder
    ) {
        LocalDate today = LocalDate.now();
        predicates.add(builder.equal(root.get("nextReviewDate"), today));
    }
}
