package com.example.mynt_finance_backend.learningPlatformTests.mappers;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.ChoiceDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.ChoiceEntity;
import com.example.mynt_finance_backend.learningPlatform.mappers.Impl.ChoiceMapperImpl;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.ChoiceTestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ChoiceMapperUnitTests {

    private final Mapper<ChoiceDTO, ChoiceEntity> testMapper;

    public ChoiceMapperUnitTests() {
        this.testMapper = new ChoiceMapperImpl();
    }

    @Test
    public void testMapToEmptyChoiceDTO() {
        ChoiceEntity choiceEntity = ChoiceTestUtil.createEmptyChoice();
        ChoiceDTO mappedChoice = testMapper.mapToDTO(choiceEntity);
        assertThat(mappedChoice.id()).isNull();
        assertThat(mappedChoice.choiceText()).isEqualTo(choiceEntity.getChoiceText());
        assertThat(mappedChoice.isCorrect()).isEqualTo(choiceEntity.getCorrect());
    }

    @Test
    public void testMapToEmptyChoiceEntity() {
        ChoiceDTO choiceDTO = ChoiceTestUtil.createEmptyChoiceDTO();
        ChoiceEntity mappedEntity = testMapper.mapToEntity(choiceDTO);
        assertThat(mappedEntity.getId()).isNull();
        assertThat(mappedEntity.getChoiceText()).isEqualTo(choiceDTO.choiceText());
        assertThat(mappedEntity.getCorrect()).isEqualTo(choiceDTO.isCorrect());
    }

    @Test
    public void testMapToFullChoiceDTO() {
        ChoiceEntity choiceEntity = ChoiceTestUtil.createEmptyChoice();
        choiceEntity.setId(1);
        ChoiceDTO mappedDTO = testMapper.mapToDTO(choiceEntity);
        assertThat(mappedDTO.id()).isEqualTo(1);
        assertThat(mappedDTO.choiceText()).isEqualTo(choiceEntity.getChoiceText());
        assertThat(mappedDTO.isCorrect()).isEqualTo(choiceEntity.getCorrect());
    }

    @Test
    public void testMapToFullChoiceEntity() {
        ChoiceDTO choiceDTO = ChoiceTestUtil.createFullChoiceDTO();
        ChoiceEntity mappedEntity = testMapper.mapToEntity(choiceDTO);
        assertThat(mappedEntity.getId()).isNull();
        assertThat(mappedEntity.getChoiceText()).isEqualTo(choiceDTO.choiceText());
        assertThat(mappedEntity.getCorrect()).isEqualTo(choiceDTO.isCorrect());
    }
}
