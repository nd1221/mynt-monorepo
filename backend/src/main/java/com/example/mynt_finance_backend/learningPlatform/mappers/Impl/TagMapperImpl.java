package com.example.mynt_finance_backend.learningPlatform.mappers.Impl;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.TagDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.CourseEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.LearningPathEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TagEntity;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import org.springframework.stereotype.Component;

@Component
public class TagMapperImpl implements Mapper<TagDTO, TagEntity> {

    @Override
    public TagDTO mapToDTO(TagEntity tagEntity) {
        return new TagDTO(
                tagEntity.getId(),
                tagEntity.getTag(),
                getChildEntitySet(tagEntity.getLearningPaths(), LearningPathEntity::getId),
                getChildEntitySet(tagEntity.getCourses(), CourseEntity::getId)
        );
    }

    @Override
    public TagEntity mapToEntity(TagDTO tagDTO) {
        return new TagEntity(
                null,
                tagDTO.tag(),
                null,
                null
        );
    }
}
