package com.example.mynt_finance_backend.learningPlatform.domain.DTOs;

import java.util.Optional;
import java.util.Set;

public record LessonDTO(
        Integer id,
        String title,
        String description,
        Integer position,
        Optional<Integer> sectionId,
        int courseId,
        Set<Long> questionIds,
        int duration,
        int numberOfLessonsInCourse,
        int numberOfQuestionsInLesson,
        int numberOfCoreQuestionsInLesson
) {
}
