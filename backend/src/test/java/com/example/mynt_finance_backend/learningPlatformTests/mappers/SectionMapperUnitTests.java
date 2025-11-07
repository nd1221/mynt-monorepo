package com.example.mynt_finance_backend.learningPlatformTests.mappers;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.SectionDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.LessonEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.SectionEntity;
import com.example.mynt_finance_backend.learningPlatform.mappers.Impl.SectionMapperImpl;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.SectionTestUtil;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class SectionMapperUnitTests {

    private final Mapper<SectionDTO, SectionEntity> testMapper;

    public SectionMapperUnitTests() {
        this.testMapper = new SectionMapperImpl();
    }

//    @Test
//    public void testMapEmptySectionEntityToDTO() {
//        SectionEntity sectionEntity = SectionTestUtil.createEmptySection();
//        SectionDTO mappedDTO = testMapper.mapToDTO(sectionEntity);
//        assertThat(mappedDTO.title()).isEqualTo(sectionEntity.getTitle());
//        assertThat(mappedDTO.description()).isEqualTo(sectionEntity.getDescription());
//        assertThat(mappedDTO.position()).isEqualTo(sectionEntity.getPosition());
//        assertThat(mappedDTO.id()).isNull();
//        assertThat(mappedDTO.courseId()).isEmpty();
//        assertThat(mappedDTO.lessonIds()).isEmpty();
//        assertThat(mappedDTO.testId()).isEmpty();
//    }

    @Test
    public void testMapEmptySectionDTOToEntity() {
        SectionDTO sectionDTO = SectionTestUtil.createEmptySectionDTO();
        SectionEntity mappedEntity = testMapper.mapToEntity(sectionDTO);
        assertThat(mappedEntity.getTitle()).isEqualTo(sectionDTO.title());
        assertThat(mappedEntity.getDescription()).isEqualTo(sectionDTO.description());
        assertThat(mappedEntity.getPosition()).isEqualTo(sectionDTO.position());
        assertThat(mappedEntity.getId()).isNull();
        assertThat(mappedEntity.getCourse()).isNull();
        assertThat(mappedEntity.getLessons()).isNull();
        assertThat(mappedEntity.getTest()).isNull();
    }

    @Test
    public void testMapFullSectionEntityToDTO() {
        SectionEntity sectionEntity = SectionTestUtil.createFullSection();
        SectionDTO mappedDTO = testMapper.mapToDTO(sectionEntity);
        assertThat(mappedDTO.id()).isEqualTo(sectionEntity.getId());
        assertThat(mappedDTO.title()).isEqualTo(sectionEntity.getTitle());
        assertThat(mappedDTO.description()).isEqualTo(sectionEntity.getDescription());
        assertThat(mappedDTO.position()).isEqualTo(sectionEntity.getPosition());
        assertThat(mappedDTO.courseId()).isPresent();
        assertThat(mappedDTO.courseId().get()).isEqualTo(sectionEntity.getCourse().getId());
        assertThat(mappedDTO.lessonIds()).isEqualTo(sectionEntity.getLessons().stream().map(LessonEntity::getId).collect(Collectors.toSet()));
        assertThat(mappedDTO.testId()).isPresent();
        assertThat(mappedDTO.testId().get()).isEqualTo(sectionEntity.getTest().getId());
    }

    @Test
    public void testMapFullSectionDTOToEntity() {
        SectionDTO sectionDTO = SectionTestUtil.createFullSectionDTO();
        SectionEntity mappedEntity = testMapper.mapToEntity(sectionDTO);
        assertThat(mappedEntity.getId()).isNull();
        assertThat(mappedEntity.getTitle()).isEqualTo(sectionDTO.title());
        assertThat(mappedEntity.getDescription()).isEqualTo(sectionDTO.description());
        assertThat(mappedEntity.getPosition()).isEqualTo(sectionDTO.position());
        assertThat(mappedEntity.getCourse()).isNull();
        assertThat(mappedEntity.getLessons()).isNull();
        assertThat(mappedEntity.getTest()).isNull();
    }
}
