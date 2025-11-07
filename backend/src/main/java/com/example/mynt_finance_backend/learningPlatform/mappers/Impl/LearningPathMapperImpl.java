package com.example.mynt_finance_backend.learningPlatform.mappers.Impl;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.LearningPathDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.CourseEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.LearningPathEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TagEntity;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import org.springframework.stereotype.Component;

@Component
public class LearningPathMapperImpl implements Mapper<LearningPathDTO, LearningPathEntity> {

    @Override
    public LearningPathDTO mapToDTO(LearningPathEntity learningPathEntity) {
        return new LearningPathDTO(
                learningPathEntity.getId(),
                learningPathEntity.getTitle(),
                learningPathEntity.getDescription(),
                learningPathEntity.getDifficulty(),
                getChildEntitySet(learningPathEntity.getCourses(), CourseEntity::getId),
                getChildEntitySet(learningPathEntity.getTags(), TagEntity::getTag),
                learningPathEntity.getCreators(),
                learningPathEntity.getNumberOfEnrolledUsers(),
                learningPathEntity.getIconURL(),
                learningPathEntity.getCreatedAt(),
                learningPathEntity.getLastUpdatedAt()
        );
    }

    @Override
    public LearningPathEntity mapToEntity(LearningPathDTO learningPathDTO) {
        return new LearningPathEntity(
                null,
                learningPathDTO.title(),
                learningPathDTO.description(),
                learningPathDTO.difficulty(),
                null,
                null,
                learningPathDTO.creators(),
                learningPathDTO.iconURL(),
                learningPathDTO.lastUpdatedAt()
        );
    }
}
