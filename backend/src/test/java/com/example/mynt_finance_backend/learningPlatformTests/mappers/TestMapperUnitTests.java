package com.example.mynt_finance_backend.learningPlatformTests.mappers;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.TestDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.QuestionEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TestEntity;
import com.example.mynt_finance_backend.learningPlatform.mappers.Impl.TestMapperImpl;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.TestTestEntityUtil;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class TestMapperUnitTests {

    private final Mapper<TestDTO, TestEntity> testMapper;

    public TestMapperUnitTests() {
        this.testMapper = new TestMapperImpl();
    }

    @Test
    public void testMapEmptyTestEntityToDTO() {
        TestEntity testEntity = TestTestEntityUtil.createEmptyTest();
        TestDTO mappedDTO = testMapper.mapToDTO(testEntity);
        assertThat(mappedDTO.numberOfQuestions()).isEqualTo(testEntity.getNumberOfQuestions());
        assertThat(mappedDTO.id()).isNull();
        assertThat(mappedDTO.timeLimit()).isNull();
        assertThat(mappedDTO.questionIds()).isEmpty();
    }

    @Test
    public void testMapEmptyTestDTOToEntity() {
        TestDTO testDTO = TestTestEntityUtil.createEmptyTestDTO();
        TestEntity mappedEntity = testMapper.mapToEntity(testDTO);
        assertThat(mappedEntity.getNumberOfQuestions()).isEqualTo(testDTO.numberOfQuestions());
        assertThat(mappedEntity.getId()).isNull();
        assertThat(mappedEntity.getTimeLimit()).isEqualTo(testDTO.timeLimit());
        assertThat(mappedEntity.getQuestions()).isNull();
    }

    @Test
    public void testMapFullTestEntityToDTO() {
        TestEntity testEntity = TestTestEntityUtil.createFullTest();
        TestDTO mappedDTO = testMapper.mapToDTO(testEntity);
        assertThat(mappedDTO.id()).isEqualTo(testEntity.getId());
        assertThat(mappedDTO.numberOfQuestions()).isEqualTo(testEntity.getNumberOfQuestions());
        assertThat(mappedDTO.timeLimit()).isEqualTo(testEntity.getTimeLimit());
        assertThat(mappedDTO.questionIds()).isEqualTo(testEntity.getQuestions().stream().map(QuestionEntity::getId).collect(Collectors.toSet()));
    }

    @Test
    public void testMapFullDTOToEntity() {
        TestDTO testDTO = TestTestEntityUtil.createFullTestDTO();
        TestEntity mappedEntity = testMapper.mapToEntity(testDTO);
        assertThat(mappedEntity.getId()).isNull();
        assertThat(mappedEntity.getNumberOfQuestions()).isEqualTo(testDTO.numberOfQuestions());
        assertThat(mappedEntity.getTimeLimit()).isEqualTo(testDTO.timeLimit());
        assertThat(mappedEntity.getQuestions()).isNull();
    }
}
