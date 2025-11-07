package com.example.mynt_finance_backend.progressTracking.services.utils;

import com.example.mynt_finance_backend.progressTracking.controllers.utils.CustomSessionSearchParam;
import com.example.mynt_finance_backend.progressTracking.domain.entities.AggregateQuestionTracker;
import com.example.mynt_finance_backend.progressTracking.domain.entities.QuestionProgressTracker;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

public class CustomSessionSpecification {

    public static Specification<QuestionProgressTracker> applyCustomSessionFilters(CustomSessionSearchParam params, long courseProgressTrackerId) {

        return (root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();

            // Necessary joins
            // AggregateQuestionTracker
            Join<QuestionProgressTracker, AggregateQuestionTracker> aqtJoin = root.join("aggregateQuestionTracker", JoinType.LEFT);

            // Filters
            applyCourseTrackerPredicate(predicates, root, builder, courseProgressTrackerId);
            applySectionAndLessonPredicate(predicates, root, builder, params);
            applyDifficultyPredicate(predicates, root, builder, params, aqtJoin);

            // Sort
            sort(params.priority(), root, query, builder, aqtJoin);

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
            CustomSessionSearchParam params
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
            CustomSessionSearchParam params,
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

    private static String extractSortParam(int priority) {
        return switch (priority) {
            case 0 -> "nextReviewDate";
            case 1, 2 -> "accuracy";
            case 3 -> "numberOfAttempts";
            default -> "totalCount";
        };
    }

    private static List<Order> applySort(
            Order order,
            Root<QuestionProgressTracker> root,
            CriteriaBuilder builder
    ) {
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        orders.addAll(applyFallbackSort(root, builder));
        return orders;
    }

    private static List<Order> applyFallbackSort(
            Root<QuestionProgressTracker> root,
            CriteriaBuilder builder
    ) {
        return List.of(
                builder.asc(root.get("firstReviewedDate")),
                builder.asc(root.get("id"))
        );
    }

    private static void sort(
         int priority,
         Root<QuestionProgressTracker> root,
         CriteriaQuery<?> query,
         CriteriaBuilder builder,
         Join<QuestionProgressTracker, AggregateQuestionTracker> join
    ) {
        if (priority == 1 || priority == 3) {
            query.orderBy(
                    applySort(builder.asc(join.get(extractSortParam(priority))), root, builder)
            );
        } else {
            if (priority == 4 || priority == 5) {
                query.orderBy(
                        priority == 4 ?
                                applySort(builder.asc(root.get(extractSortParam(priority))), root, builder) :
                                applySort(builder.desc(root.get(extractSortParam(priority))), root, builder)
                );
            } else {
                query.orderBy(
                        applySort(builder.asc(root.get(extractSortParam(priority))), root, builder)
                );
            }
        }
    }
}
