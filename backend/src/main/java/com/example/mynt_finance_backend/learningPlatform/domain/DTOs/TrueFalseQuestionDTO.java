package com.example.mynt_finance_backend.learningPlatform.domain.DTOs;

import com.example.mynt_finance_backend.learningPlatform.domain.entityEnums.QuestionType;

import java.util.Optional;

public class TrueFalseQuestionDTO extends QuestionDTO {

    private final Boolean isCorrect;

    public TrueFalseQuestionDTO(Long id, String questionText, QuestionType questionType, Optional<Integer> lessonId, int lessonNumber, boolean core, int lessonPosition, int sectionPosition, boolean locked, Boolean isCorrect) {
        super(id, questionText, questionType, lessonId, lessonNumber, core, lessonPosition, sectionPosition, locked);
        this.isCorrect = isCorrect;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }
}
