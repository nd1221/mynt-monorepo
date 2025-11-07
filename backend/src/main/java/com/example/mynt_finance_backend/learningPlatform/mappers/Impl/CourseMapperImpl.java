package com.example.mynt_finance_backend.learningPlatform.mappers.Impl;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.CourseDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.*;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import org.springframework.stereotype.Component;

@Component
public class CourseMapperImpl implements Mapper<CourseDTO, CourseEntity> {

    @Override
    public CourseDTO mapToDTO(CourseEntity courseEntity) {
        return new CourseDTO(
                courseEntity.getId(),
                courseEntity.getTitle(),
                courseEntity.getDescription(),
                courseEntity.getDifficulty(),
                getChildEntitySet(courseEntity.getSections(), SectionEntity::getId),
                getChildEntitySet(courseEntity.getTags(), TagEntity::getTag),
                courseEntity.getCreators(),
                courseEntity.getNumberOfEnrolledUsers(),
                courseEntity.getIconURL(),
                courseEntity.getCreatedAt(),
                courseEntity.getLastUpdatedAt(),
                courseEntity.getObjectives(),
                courseEntity.getRequirements(),
                getDuration(courseEntity)
        );
    }

    public CourseEntity mapToEntity(CourseDTO courseDTO) {
        return new CourseEntity(
                null,
                courseDTO.title(),
                courseDTO.description(),
                courseDTO.difficulty(),
                null,
                null,
                courseDTO.creators(),
                0,
                courseDTO.iconURL(),
                courseDTO.createdAt(),
                courseDTO.lastUpdatedAt(),
                null,
                null
        );
    }

    private int getDuration(CourseEntity course) {
        return course.getSections().stream()
                .flatMap(section -> section.getLessons().stream())
                .mapToInt(LessonEntity::getDuration)
                .sum();
    }
}
