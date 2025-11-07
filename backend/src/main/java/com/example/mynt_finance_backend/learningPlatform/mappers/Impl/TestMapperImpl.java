package com.example.mynt_finance_backend.learningPlatform.mappers.Impl;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.TestDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.QuestionEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.SectionEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TestEntity;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import org.springframework.stereotype.Component;

@Component
public class TestMapperImpl implements Mapper<TestDTO, TestEntity> {

    @Override
    public TestDTO mapToDTO(TestEntity testEntity) {
        return new TestDTO(
                testEntity.getId(),
                getChildEntityId(testEntity.getSection(), SectionEntity::getId),
                testEntity.getNumberOfQuestions(),
                testEntity.getTimeLimit(),
                getChildEntitySet(testEntity.getQuestions(), QuestionEntity::getId)
        );
    }

    @Override
    public TestEntity mapToEntity(TestDTO testDTO) {
        return new TestEntity(
                null,
                null,
                testDTO.numberOfQuestions(),
                testDTO.timeLimit(),
                null
        );
    }
}
