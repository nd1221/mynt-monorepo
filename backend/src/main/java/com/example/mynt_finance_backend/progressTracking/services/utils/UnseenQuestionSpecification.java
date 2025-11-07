package com.example.mynt_finance_backend.progressTracking.services.utils;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.QuestionEntity;
import com.example.mynt_finance_backend.progressTracking.controllers.utils.CustomSessionSearchParam;
import com.example.mynt_finance_backend.progressTracking.controllers.utils.ReviewSessionSearchParam;
import com.example.mynt_finance_backend.progressTracking.domain.entities.AggregateQuestionTracker;
import com.example.mynt_finance_backend.progressTracking.domain.entities.QuestionProgressTracker;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UnseenQuestionSpecification {

    public static Specification<QuestionEntity> applyUnseenQuestionFilters(ReviewSessionSearchParam params, long courseProgressTrackerId, boolean complete) {

        return (root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();

            // Necessary joins
            // QuestionProgressTracker
            Join<QuestionEntity, QuestionProgressTracker> qptJoin = root.join("questionProgressTrackers", JoinType.LEFT);
            qptJoin.on(
                    builder.equal(
                            qptJoin.get("lessonProgressTracker").get("courseProgressTracker").get("id"),
                            courseProgressTrackerId
                    )
            );
            // AggregateQuestionTracker
            Join<QuestionEntity, AggregateQuestionTracker> aqtJoin = root.join("aggregateQuestionTracker", JoinType.LEFT);

            // Filters
            applySectionAndLessonPredicate(predicates, root, builder, params);
            applyCorePredicate(predicates, root, builder, complete);
            applyDifficultyPredicate(predicates, builder, params, aqtJoin);
            applyAnsweredPredicate(predicates, builder, qptJoin);

            // Sort
            // NO PARTICULAR ORDER

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<QuestionEntity> applyCustomUnseenQuestionFilters(CustomSessionSearchParam params, long courseProgressTrackerId, boolean complete) {

        return (root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();

            // Necessary joins
            // QuestionProgressTracker
            Join<QuestionEntity, QuestionProgressTracker> qptJoin = root.join("questionProgressTrackers", JoinType.LEFT);
            qptJoin.on(
                    builder.equal(
                            qptJoin.get("lessonProgressTracker").get("courseProgressTracker").get("id"),
                            courseProgressTrackerId
                    )
            );
            // AggregateQuestionTracker
            Join<QuestionEntity, AggregateQuestionTracker> aqtJoin = root.join("aggregateQuestionTracker", JoinType.LEFT);

            // Filters
            applySectionAndLessonPredicate(predicates, root, builder, params);
            applyCorePredicate(predicates, root, builder, complete);
            applyDifficultyPredicate(predicates, builder, params, aqtJoin);
            applyAnsweredPredicate(predicates, builder, qptJoin);

            // Sort
            sort(params.priority(), root, query, builder, aqtJoin, qptJoin);

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static void applySectionAndLessonPredicate(
            List<Predicate> predicates,
            Root<QuestionEntity> root,
            CriteriaBuilder builder,
            ReviewSessionSearchParam params
    ) {
        if (params.section() != null && params.section() != 0) {
            // lesson predicate
            if (params.lesson() != null && params.lesson() != 0) {
                predicates.add(builder.equal(root.get("lesson").get("id"), params.lesson()));
            } else {
                predicates.add(builder.equal(root.get("lesson").get("section").get("id"), params.section()));
            }
        }
    }

    public static void applySectionAndLessonPredicate(
            List<Predicate> predicates,
            Root<QuestionEntity> root,
            CriteriaBuilder builder,
            CustomSessionSearchParam params
    ) {
        if (params.section() != null && params.section() != 0) {
            // lesson predicate
            if (params.lesson() != null && params.lesson() != 0) {
                predicates.add(builder.equal(root.get("lesson").get("id"), params.lesson()));
            } else {
                predicates.add(builder.equal(root.get("lesson").get("section").get("id"), params.section()));
            }
        }
    }

    public static void applyDifficultyPredicate(
            List<Predicate> predicates,
            CriteriaBuilder builder,
            ReviewSessionSearchParam params,
            Join<QuestionEntity, AggregateQuestionTracker> join
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

    public static void applyDifficultyPredicate(
            List<Predicate> predicates,
            CriteriaBuilder builder,
            CustomSessionSearchParam params,
            Join<QuestionEntity, AggregateQuestionTracker> join
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

    private static void applyAnsweredPredicate(
            List<Predicate> predicates,
            CriteriaBuilder builder,
            Join<QuestionEntity, QuestionProgressTracker> join
    ) {
        // If QPT does not exist, then user has never answered question before
        Predicate qptDoesNotExist = builder.isNull(
                join.get("id")
        );
        predicates.add(qptDoesNotExist);
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
            Root<QuestionEntity> root,
            CriteriaBuilder builder
    ) {
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        orders.addAll(applyFallbackSort(root, builder));
        return orders;
    }

    private static List<Order> applyFallbackSort(
            Root<QuestionEntity> root,
            CriteriaBuilder builder
    ) {
        return List.of(
                builder.asc(root.get("id"))
        );
    }

    private static void sort(
            int priority,
            Root<QuestionEntity> root,
            CriteriaQuery<?> query,
            CriteriaBuilder builder,
            Join<QuestionEntity, AggregateQuestionTracker> aqtJoin,
            Join<QuestionEntity, QuestionProgressTracker> qptJoin
    ) {
        if (priority == 0) {
            query.orderBy(applyFallbackSort(root, builder));
        } else if (priority == 1 || priority == 3) {
            query.orderBy(
                    applySort(builder.asc(aqtJoin.get(extractSortParam(priority))), root, builder)
            );
        } else {
            if (priority == 4 || priority == 5) {
                query.orderBy(
                        priority == 4 ?
                                applySort(builder.asc(qptJoin.get(extractSortParam(priority))), root, builder) :
                                applySort(builder.desc(qptJoin.get(extractSortParam(priority))), root, builder)
                );
            } else {
                query.orderBy(
                        applySort(builder.asc(qptJoin.get(extractSortParam(priority))), root, builder)
                );
            }
        }
    }

    private static void applyCorePredicate(
            List<Predicate> predicates,
            Root<QuestionEntity> root,
            CriteriaBuilder builder,
            boolean complete
    ) {
        if (!complete) {
            predicates.add(builder.equal(root.get("core"), true));
        }
    }
}
