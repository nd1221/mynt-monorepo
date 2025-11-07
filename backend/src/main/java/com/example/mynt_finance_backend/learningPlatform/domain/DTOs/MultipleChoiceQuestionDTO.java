package com.example.mynt_finance_backend.learningPlatform.domain.DTOs;

import com.example.mynt_finance_backend.learningPlatform.domain.entityEnums.QuestionType;

import java.util.Optional;
import java.util.Set;

public class MultipleChoiceQuestionDTO extends QuestionDTO {

    private final Set<ChoiceDTO> choices;

    public MultipleChoiceQuestionDTO(Long id, String questionText, QuestionType questionType, Optional<Integer> lessonId, int lessonNumber, boolean core, int lessonPosition, int sectionPosition, boolean locked, Set<ChoiceDTO> choices) {
        super(id, questionText, questionType, lessonId, lessonNumber, core, lessonPosition, sectionPosition, locked);
        this.choices = choices;
    }

    public Set<ChoiceDTO> getChoices() {
        return choices;
    }
}
