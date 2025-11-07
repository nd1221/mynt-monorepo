package com.example.mynt_finance_backend.learningPlatform.mappers.Impl;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.LessonDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.CourseEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.LessonEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.QuestionEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.SectionEntity;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import org.springframework.stereotype.Component;

@Component
public class LessonMapperImpl implements Mapper<LessonDTO, LessonEntity> {

    @Override
    public LessonDTO mapToDTO(LessonEntity lessonEntity) {
        return new LessonDTO(
                lessonEntity.getId(),
                lessonEntity.getTitle(),
                lessonEntity.getDescription(),
                lessonEntity.getPosition(),
                getChildEntityId(lessonEntity.getSection(), SectionEntity::getId),
                getChildEntityId(lessonEntity.getSection().getCourse(), CourseEntity::getId).get(),
                getChildEntitySet(lessonEntity.getQuestions(), QuestionEntity::getId),
                lessonEntity.getDuration(),
                lessonEntity.getSection().getCourse().getSections().stream()
                        .mapToInt(section -> section.getLessons().size())
                        .sum(),
                lessonEntity.getQuestions().size(),
                lessonEntity.getQuestions().stream().filter(QuestionEntity::isCore).toList().size()
        );
    }

    @Override
    public LessonEntity mapToEntity(LessonDTO lessonDTO) {
        return new LessonEntity(
                null,
                lessonDTO.title(),
                lessonDTO.description(),
                lessonDTO.position(),
                null,
                null,
                null,
                lessonDTO.duration()
        );
    }
}
