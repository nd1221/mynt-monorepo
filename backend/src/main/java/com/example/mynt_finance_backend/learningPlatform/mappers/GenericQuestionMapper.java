package com.example.mynt_finance_backend.learningPlatform.mappers;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.MultipleChoiceQuestionDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.TrueFalseQuestionDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.MultipleChoiceQuestionEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TrueFalseQuestionEntity;

public interface GenericQuestionMapper {

    MultipleChoiceQuestionDTO mapToDTO(MultipleChoiceQuestionEntity questionEntity, boolean locked);

    TrueFalseQuestionDTO mapToDTO(TrueFalseQuestionEntity questionEntity, boolean locked);

    MultipleChoiceQuestionEntity mapToEntity(MultipleChoiceQuestionDTO questionDTO);

    TrueFalseQuestionEntity mapToEntity(TrueFalseQuestionDTO questionDTO);
}
