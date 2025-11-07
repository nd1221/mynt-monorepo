package com.example.mynt_finance_backend.progressTracking.domain.dtos;

import org.springframework.data.domain.Page;

public record QuestionBankPageDTO(
        Page<QuestionProgressAggregateDTO> page,
        Meta meta
) {
    public record Meta(
            Long numberOfCourseQuestions,
            Long numberOfSectionQuestions,
            Long numberOfLessonQuestions
    ) {}
}
