package com.example.mynt_finance_backend.learningPlatform.mappers.Impl;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.ChoiceDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.MultipleChoiceQuestionDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.*;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MultipleChoiceQuestionMapperImpl implements QuestionMapper<MultipleChoiceQuestionDTO, MultipleChoiceQuestionEntity> {

    @Autowired
    private Mapper<ChoiceDTO, ChoiceEntity> choiceMapper;

    @Override
    public MultipleChoiceQuestionDTO mapToDTO(MultipleChoiceQuestionEntity questionEntity, boolean locked) {
        return new MultipleChoiceQuestionDTO(
                questionEntity.getId(),
                questionEntity.getQuestionText(),
                questionEntity.getQuestionType(),
                Optional.of(questionEntity.getLesson().getId()),
                questionEntity.getLessonNumber(),
                questionEntity.isCore(),
                questionEntity.getLesson().getPosition(),
                questionEntity.getLesson().getSection().getPosition(),
                locked,
                questionEntity.getChoices().stream().map(choiceMapper::mapToDTO).collect(Collectors.toSet())
        );
    }

    @Override
    public MultipleChoiceQuestionEntity mapToEntity(MultipleChoiceQuestionDTO questionDTO) {
        return new MultipleChoiceQuestionEntity(
                null,
                questionDTO.getQuestionText(),
                questionDTO.getQuestionType(),
                null,
                questionDTO.getLessonNumber(),
                questionDTO.getCore(),
                null
        );
    }
}
