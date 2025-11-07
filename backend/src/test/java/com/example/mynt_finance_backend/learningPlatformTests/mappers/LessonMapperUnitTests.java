package com.example.mynt_finance_backend.learningPlatformTests.mappers;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.LessonDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.LessonEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.QuestionEntity;
import com.example.mynt_finance_backend.learningPlatform.mappers.Impl.LessonMapperImpl;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.LessonTestUtil;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class LessonMapperUnitTests {

    private final Mapper<LessonDTO, LessonEntity> testMapper;

    public LessonMapperUnitTests() {
        this.testMapper = new LessonMapperImpl();
    }

//    @Test
//    public void testMapEmptyLessonEntityToDTO() {
//        LessonEntity lessonEntity = LessonTestUtil.createEmptyLesson();
//        LessonDTO mappedDTO = testMapper.mapToDTO(lessonEntity);
//        assertThat(mappedDTO.title()).isEqualTo(lessonEntity.getTitle());
//        assertThat(mappedDTO.description()).isEqualTo(lessonEntity.getDescription());
//        assertThat(mappedDTO.position()).isEqualTo(lessonEntity.getPosition());
//        assertThat(mappedDTO.id()).isNull();
//        assertThat(mappedDTO.sectionId()).isEmpty();
//        assertThat(mappedDTO.questionIds()).isEmpty();
//    }

    @Test
    public void testMapEmptyLessonDTOToEntity() {
        LessonDTO lessonDTO = LessonTestUtil.createEmptyLessonDTO();
        LessonEntity mappedEntity = testMapper.mapToEntity(lessonDTO);
        assertThat(mappedEntity.getTitle()).isEqualTo(lessonDTO.title());
        assertThat(mappedEntity.getDescription()).isEqualTo(lessonDTO.description());
        assertThat(mappedEntity.getPosition()).isEqualTo(lessonDTO.position());
        assertThat(mappedEntity.getId()).isNull();
        assertThat(mappedEntity.getSection()).isNull();
        assertThat(mappedEntity.getQuestions()).isNull();
    }

//    @Test
//    public void testMapFullLessonEntityToDTO() {
//        LessonEntity lessonEntity = LessonTestUtil.createFullLesson();
//        LessonDTO mappedDTO = testMapper.mapToDTO(lessonEntity);
//        assertThat(mappedDTO.id()).isEqualTo(lessonEntity.getId());
//        assertThat(mappedDTO.title()).isEqualTo(lessonEntity.getTitle());
//        assertThat(mappedDTO.description()).isEqualTo(lessonEntity.getDescription());
//        assertThat(mappedDTO.position()).isEqualTo(lessonEntity.getPosition());
//        assertThat(mappedDTO.sectionId()).isPresent();
//        assertThat(mappedDTO.sectionId().get()).isEqualTo(lessonEntity.getSection().getId());
//        assertThat(mappedDTO.questionIds()).isEqualTo(lessonEntity.getQuestions().stream().map(QuestionEntity::getId).collect(Collectors.toSet()));
//    }

    @Test
    public void testMapFullLessonDTOToEntity() {
        LessonDTO lessonDTO = LessonTestUtil.createFullLessonDTO();
        LessonEntity mappedEntity = testMapper.mapToEntity(lessonDTO);
        assertThat(mappedEntity.getId()).isNull();
        assertThat(mappedEntity.getTitle()).isEqualTo(lessonDTO.title());
        assertThat(mappedEntity.getDescription()).isEqualTo(lessonDTO.description());
        assertThat(mappedEntity.getPosition()).isEqualTo(lessonDTO.position());
        assertThat(mappedEntity.getSection()).isNull();
        assertThat(mappedEntity.getQuestions()).isNull();
    }
}
