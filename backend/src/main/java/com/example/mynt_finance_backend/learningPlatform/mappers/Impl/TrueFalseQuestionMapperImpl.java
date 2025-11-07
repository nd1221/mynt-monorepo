package com.example.mynt_finance_backend.learningPlatform.mappers.Impl;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.TrueFalseQuestionDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.*;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TrueFalseQuestionMapperImpl implements QuestionMapper<TrueFalseQuestionDTO, TrueFalseQuestionEntity> {

    @Override
    public TrueFalseQuestionDTO mapToDTO(TrueFalseQuestionEntity questionEntity, boolean locked) {
        return new TrueFalseQuestionDTO(
                questionEntity.getId(),
                questionEntity.getQuestionText(),
                questionEntity.getQuestionType(),
                Optional.of(questionEntity.getLesson().getId()),
                questionEntity.getLessonNumber(),
                questionEntity.isCore(),
                questionEntity.getLesson().getPosition(),
                questionEntity.getLesson().getSection().getPosition(),
                locked,
                questionEntity.getCorrect()
        );
    }

    @Override
    public TrueFalseQuestionEntity mapToEntity(TrueFalseQuestionDTO questionDTO) {
        return new TrueFalseQuestionEntity(
                null,
                questionDTO.getQuestionText(),
                questionDTO.getQuestionType(),
                null,
                questionDTO.getLessonNumber(),
                questionDTO.getCore(),
                questionDTO.getCorrect()
        );
    }
}
