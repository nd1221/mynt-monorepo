package com.example.mynt_finance_backend.learningPlatform.mappers.Impl;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.ChoiceDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.ChoiceEntity;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import org.springframework.stereotype.Component;

@Component
public class ChoiceMapperImpl implements Mapper<ChoiceDTO, ChoiceEntity> {

    @Override
    public ChoiceDTO mapToDTO(ChoiceEntity choiceEntity) {
        return new ChoiceDTO(
                choiceEntity.getId(),
                choiceEntity.getChoiceText(),
                choiceEntity.getCorrect()
        );
    }

    @Override
    public ChoiceEntity mapToEntity(ChoiceDTO choiceDTO) {
        return new ChoiceEntity(
                null,
                choiceDTO.choiceText(),
                choiceDTO.isCorrect(),
                null
        );
    }
}
