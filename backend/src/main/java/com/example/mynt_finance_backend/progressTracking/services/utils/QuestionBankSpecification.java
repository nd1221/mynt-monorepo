package com.example.mynt_finance_backend.progressTracking.services.utils;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.QuestionEntity;
import com.example.mynt_finance_backend.progressTracking.controllers.utils.QuestionBankSearchParam;
import com.example.mynt_finance_backend.progressTracking.domain.entities.AggregateQuestionTracker;
import com.example.mynt_finance_backend.progressTracking.domain.entities.QuestionProgressTracker;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class QuestionBankSpecification {

    public static Specification<QuestionEntity> applyFilters(QuestionBankSearchParam searchParams, Sort sort, long courseProgressTrackerId) {

        return (root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();

            // Required joins
            // QuestionProgressTracker
            Join<QuestionEntity, QuestionProgressTracker> qptJoin = root.join("questionProgressTrackers", JoinType.LEFT);
            // Filter out question trackers belonging to other users otherwise applyAnsweredPredicate will remove all questions since isNull will always return true for the other users' question tracker rows
            qptJoin.on(
                    builder.equal(
                            qptJoin.get("lessonProgressTracker").get("courseProgressTracker").get("id"),
                            courseProgressTrackerId
                    )
            );
            // AggregateQuestionTracker
            Join<QuestionEntity, AggregateQuestionTracker> aqtJoin = root.join("aggregateQuestionTracker", JoinType.LEFT);

            // Filters
            applySectionAndLessonPredicate(predicates, root, builder, searchParams);
            applyCorePredicate(predicates, root, builder, searchParams);
            applyDifficultyPredicate(predicates, root, builder, searchParams, aqtJoin);
            applyAnsweredPredicate(predicates, builder, searchParams, qptJoin, courseProgressTrackerId);

            // Sort
            sortQuestions(sort, root, query, builder, qptJoin, aqtJoin);

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static String extractSortParam(String param) {
        return switch (param) {
            case "accuracy", "myAccuracy" -> "accuracy";
            case "time", "myTime" -> "averageQuestionTime";
            case "attempts" -> "numberOfAttempts";
            case "myAttempts" -> "totalCount";
            default -> "position";
        };
    }

    private static boolean extractSortDirection(Sort.Order order) {
        return order.getDirection() == Sort.Direction.ASC;
    }

    private static void applySectionAndLessonPredicate(
            List<Predicate> predicates,
            Root<QuestionEntity> root,
            CriteriaBuilder builder,
            QuestionBankSearchParam params
    ) {

        if (params.section() != null && params.lesson() != null) {
            if (params.section() != 0) {
                if (params.lesson() != 0) {
                    predicates.add(builder.equal(root.get("lesson").get("id"), params.lesson()));
                } else {
                    predicates.add(builder.equal(root.get("lesson").get("section").get("id"), params.section()));
                }
            } else {
                if (params.lesson() != 0) {
                    predicates.add(builder.equal(root.get("lesson").get("id"), params.lesson()));
                }
            }
        }
    }

    private static void applyCorePredicate(
            List<Predicate> predicates,
            Root<QuestionEntity> root,
            CriteriaBuilder builder,
            QuestionBankSearchParam params
    ) {
        if (params.core() != null && !params.core().equals("all")) {
            if (params.core().equals("core")) {
                predicates.add(builder.equal(root.get("core"), true));
            } else {
                predicates.add(builder.equal(root.get("core"), false));
            }
        }
    }

    private static void applyDifficultyPredicate(
            List<Predicate> predicates,
            Root<QuestionEntity> root,
            CriteriaBuilder builder,
            QuestionBankSearchParam params,
            Join<QuestionEntity, AggregateQuestionTracker> join
    ) {
        if (params.difficulty() != null && !params.difficulty().equals("all")) {
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
            QuestionBankSearchParam params,
            Join<QuestionEntity, QuestionProgressTracker> join,
            long courseProgressTrackerId
    ) {
        Predicate qptExists = builder.isNotNull(
                join.get("id")
        );
        Predicate qptDoesNotExist = builder.isNull(
                join.get("id")
        );

        if (params.answered() != null) {
            switch (params.answered()) {
                case "all" -> predicates.add(builder.or(qptExists, qptDoesNotExist));
                case "answered" -> predicates.add(qptExists);
                case "unanswered" -> predicates.add(qptDoesNotExist);
                default -> {
                    // Do nothing
                }
            }
        }
    }

    private static void sortQuestions(
            Sort sort,
            Root<QuestionEntity> root,
            CriteriaQuery<?> query,
            CriteriaBuilder builder,
            Join<QuestionEntity, QuestionProgressTracker> qptJoin,
            Join<QuestionEntity, AggregateQuestionTracker> aqtJoin
    ) {
        if (!sort.isEmpty()) {

            Sort.Order order = sort.iterator().next(); // There will only ever be one sort parameter so can just grab first item in iterator
            String sortParam = order.getProperty();

            if (sortParam.equals("accuracy") || sortParam.equals("time") || sortParam.equals("attempts")) {
                query.orderBy(
                        extractSortDirection(order) ?
                                applySort(builder.asc(aqtJoin.get(extractSortParam(sortParam))), root, builder) :
                                applySort(builder.desc(aqtJoin.get(extractSortParam(sortParam))), root, builder)
                );
            } else if (sortParam.equals("myAccuracy") || sortParam.equals("myTime") || sortParam.equals("myAttempts")) {
                query.orderBy(
                        extractSortDirection(order) ?
                                applySort(builder.asc(qptJoin.get(extractSortParam(sortParam))), root, builder) :
                                applySort(builder.desc(qptJoin.get(extractSortParam(sortParam))), root, builder)
                );
            } else {
                query.orderBy(
                        applyFallbackSort(root, builder)
                );
            }
        }
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
                builder.asc(root.get("lesson").get("section").get("position")),
                builder.asc(root.get("lesson").get("position")),
                builder.asc(root.get("lessonNumber")),
                builder.asc(root.get("id"))
        );
    }
}


