package com.example.mynt_finance_backend.learningPlatform.domain.DTOs;

public record LessonContentDTO(
        LessonDTO lesson,
        String content,
        Integer prevId,
        Integer nextId,
        boolean isLastLesson,
        Long randomQuestionId
) {
}
