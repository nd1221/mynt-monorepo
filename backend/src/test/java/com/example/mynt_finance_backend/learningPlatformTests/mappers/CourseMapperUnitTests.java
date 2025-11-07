package com.example.mynt_finance_backend.learningPlatformTests.mappers;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.CourseDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.*;
import com.example.mynt_finance_backend.learningPlatform.mappers.Impl.CourseMapperImpl;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.CourseTestUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class CourseMapperUnitTests {

    private final Mapper<CourseDTO, CourseEntity> testMapper;

    public CourseMapperUnitTests() {
        this.testMapper = new CourseMapperImpl();
    }

//    @Test
//    public void testMapEmptyCourseEntityToDTO() {
//        CourseEntity courseEntity = CourseTestUtil.createEmptyCourse();
//        CourseDTO mappedDTO = testMapper.mapToDTO(courseEntity);
//        assertThat(mappedDTO.title()).isEqualTo(courseEntity.getTitle());
//        assertThat(mappedDTO.description()).isEqualTo(courseEntity.getDescription());
//        assertThat(mappedDTO.creators()).isEqualTo(courseEntity.getCreators());
//        assertThat(mappedDTO.id()).isNull();
//        assertThat(mappedDTO.difficulty()).isNull();
//        assertThat(mappedDTO.sectionIds()).isEmpty();
//        assertThat(mappedDTO.tags()).isEmpty();
//        assertThat(mappedDTO.numberOfEnrolledUsers()).isNull();
//        assertThat(mappedDTO.iconURL()).isNull();
//        assertThat(mappedDTO.createdAt()).isNull();
//        assertThat(mappedDTO.lastUpdatedAt()).isNull();
//    }

//    @Test
//    public void testMapEmptyCourseDTOToEntity() {
//        CourseDTO courseDTO = CourseTestUtil.createEmptyCourseDTO();
//        CourseEntity mappedEntity = testMapper.mapToEntity(courseDTO);
//        assertThat(mappedEntity.getTitle()).isEqualTo(courseDTO.title());
//        assertThat(mappedEntity.getDescription()).isEqualTo(courseDTO.description());
//        assertThat(mappedEntity.getCreators()).isEqualTo(courseDTO.creators());
//        assertThat(mappedEntity.getId()).isNull();
//        assertThat(mappedEntity.getDifficulty()).isEqualTo(courseDTO.difficulty());
//        assertThat(mappedEntity.getSections()).isNull();
//        assertThat(mappedEntity.getTags()).isNull();
//        assertThat(mappedEntity.getNumberOfEnrolledUsers()).isEqualTo(0);
//        assertThat(mappedEntity.getIconURL()).isEqualTo(courseDTO.iconURL());
//        assertThat(mappedEntity.getCreatedAt()).isNotNull();
//        assertThat(mappedEntity.getLastUpdatedAt()).isNull();
//    }

//    @Test
//    public void testMapFullCourseEntityToDTO() {
//        CourseEntity courseEntity = CourseTestUtil.createFullCourse();
//        CourseDTO mappedDTO = testMapper.mapToDTO(courseEntity);
//        assertThat(mappedDTO.id()).isEqualTo(courseEntity.getId());
//        assertThat(mappedDTO.title()).isEqualTo(courseEntity.getTitle());
//        assertThat(mappedDTO.description()).isEqualTo(courseEntity.getDescription());
//        assertThat(mappedDTO.difficulty()).isEqualTo(courseEntity.getDifficulty());
//        assertThat(mappedDTO.sectionIds()).isEqualTo(courseEntity.getSections().stream().map(SectionEntity::getId).collect(Collectors.toSet()));
//        assertThat(mappedDTO.tags()).isEqualTo(courseEntity.getTags().stream().map(TagEntity::getTag).collect(Collectors.toSet()));
//        assertThat(mappedDTO.creators()).isEqualTo(courseEntity.getCreators());
//        assertThat(mappedDTO.numberOfEnrolledUsers()).isEqualTo(courseEntity.getNumberOfEnrolledUsers());
//        assertThat(mappedDTO.iconURL()).isEqualTo(courseEntity.getIconURL());
//        assertThat(mappedDTO.createdAt()).isEqualTo(courseEntity.getCreatedAt());
//        assertThat(mappedDTO.lastUpdatedAt()).isEqualTo(courseEntity.getLastUpdatedAt());
//    }

    @Test
    public void testMapFullCourseDTOToEntity() {
        CourseDTO courseDTO = CourseTestUtil.createFullCourseDTO();
        CourseEntity mappedEntity = testMapper.mapToEntity(courseDTO);
        assertThat(mappedEntity.getId()).isNull();
        assertThat(mappedEntity.getTitle()).isEqualTo(courseDTO.title());
        assertThat(mappedEntity.getDescription()).isEqualTo(courseDTO.description());
        assertThat(mappedEntity.getDifficulty()).isEqualTo(courseDTO.difficulty());
        assertThat(mappedEntity.getSections()).isNull();
        assertThat(mappedEntity.getTags()).isNull();
        assertThat(mappedEntity.getCreators()).isEqualTo(courseDTO.creators());
        assertThat(mappedEntity.getNumberOfEnrolledUsers()).isEqualTo(0);
        assertThat(mappedEntity.getIconURL()).isEqualTo(courseDTO.iconURL());
        assertThat(mappedEntity.getCreatedAt()).isNotNull();
        assertThat(mappedEntity.getCreatedAt()).isInstanceOf(LocalDateTime.class);
        assertThat(mappedEntity.getLastUpdatedAt()).isEqualTo(courseDTO.lastUpdatedAt());
    }
}
