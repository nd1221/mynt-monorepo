package com.example.mynt_finance_backend.learningPlatform.mappers.Impl;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.MultipleChoiceQuestionDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.TrueFalseQuestionDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.MultipleChoiceQuestionEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TrueFalseQuestionEntity;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import com.example.mynt_finance_backend.learningPlatform.mappers.GenericQuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapperImpl implements GenericQuestionMapper {

    private final QuestionMapper<MultipleChoiceQuestionDTO, MultipleChoiceQuestionEntity> multipleChoiceQuestionMapper;

    private final QuestionMapper<TrueFalseQuestionDTO, TrueFalseQuestionEntity> trueFalseQuestionMapper;

    @Autowired
    public QuestionMapperImpl(QuestionMapper<MultipleChoiceQuestionDTO, MultipleChoiceQuestionEntity> multipleChoiceQuestionMapper, QuestionMapper<TrueFalseQuestionDTO, TrueFalseQuestionEntity> trueFalseQuestionMapper) {
        this.multipleChoiceQuestionMapper = multipleChoiceQuestionMapper;
        this.trueFalseQuestionMapper = trueFalseQuestionMapper;
    }

    @Override
    public MultipleChoiceQuestionDTO mapToDTO(MultipleChoiceQuestionEntity questionEntity, boolean locked) {
        return multipleChoiceQuestionMapper.mapToDTO(questionEntity, locked);
    }

    @Override
    public TrueFalseQuestionDTO mapToDTO(TrueFalseQuestionEntity questionEntity, boolean locked) {
        return trueFalseQuestionMapper.mapToDTO(questionEntity, locked);
    }

    @Override
    public MultipleChoiceQuestionEntity mapToEntity(MultipleChoiceQuestionDTO questionDTO) {
        return multipleChoiceQuestionMapper.mapToEntity(questionDTO);
    }

    @Override
    public TrueFalseQuestionEntity mapToEntity(TrueFalseQuestionDTO questionDTO) {
        return trueFalseQuestionMapper.mapToEntity(questionDTO);
    }
}
