package com.example.mynt_finance_backend.learningPlatform.mappers.Impl;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.SectionDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.CourseEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.LessonEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.SectionEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TestEntity;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import org.springframework.stereotype.Component;

@Component
public class SectionMapperImpl implements Mapper<SectionDTO, SectionEntity> {

    @Override
    public SectionDTO mapToDTO(SectionEntity sectionEntity) {
        return new SectionDTO(
                sectionEntity.getId(),
                sectionEntity.getTitle(),
                sectionEntity.getDescription(),
                sectionEntity.getPosition(),
                getChildEntityId(sectionEntity.getCourse(), CourseEntity::getId),
                getChildEntitySet(sectionEntity.getLessons(), LessonEntity::getId),
                getChildEntityId(sectionEntity.getTest(), TestEntity::getId),
                getDuration(sectionEntity)
        );
    }

    @Override
    public SectionEntity mapToEntity(SectionDTO sectionDTO) {
        return new SectionEntity(
                null,
                sectionDTO.title(),
                sectionDTO.description(),
                sectionDTO.position(),
                null,
                null,
                null
        );
    }

    private int getDuration(SectionEntity section) {
        return section.getLessons().stream()
                .mapToInt(LessonEntity::getDuration)
                .sum();
    }
}
