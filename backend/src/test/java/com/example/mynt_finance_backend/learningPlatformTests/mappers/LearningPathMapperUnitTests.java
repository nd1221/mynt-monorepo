package com.example.mynt_finance_backend.learningPlatformTests.mappers;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.LearningPathDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.CourseEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.LearningPathEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TagEntity;
import com.example.mynt_finance_backend.learningPlatform.mappers.Impl.LearningPathMapperImpl;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.LearningPathTestUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class LearningPathMapperUnitTests {

    private final Mapper<LearningPathDTO, LearningPathEntity> testMapper;

    public LearningPathMapperUnitTests() {
        this.testMapper = new LearningPathMapperImpl();
    }

    @Test
    public void testMapEmptyLearningPathEntityToDTO() {
        LearningPathEntity learningPathEntity = LearningPathTestUtil.createEmptyLearningPath();
        LearningPathDTO mappedDTO = testMapper.mapToDTO(learningPathEntity);
        assertThat(mappedDTO.title()).isEqualTo(learningPathEntity.getTitle());
        assertThat(mappedDTO.description()).isEqualTo(learningPathEntity.getDescription());
        assertThat(mappedDTO.creators()).isEqualTo(learningPathEntity.getCreators());
        assertThat(mappedDTO.id()).isNull();
        assertThat(mappedDTO.courseIds()).isEmpty();
        assertThat(mappedDTO.tags()).isEmpty();
        assertThat(mappedDTO.numberOfEnrolledUsers()).isNull();
        assertThat(mappedDTO.iconURL()).isNull();
        assertThat(mappedDTO.createdAt()).isNull();
        assertThat(mappedDTO.lastUpdatedAt()).isNull();
    }

    @Test
    public void testMapEmptyLearningPathDTOToEntity() {
        LearningPathDTO learningPathDTO = LearningPathTestUtil.createEmptyLearningPathDTO();
        LearningPathEntity mappedEntity = testMapper.mapToEntity(learningPathDTO);
        assertThat(mappedEntity.getTitle()).isEqualTo(learningPathDTO.title());
        assertThat(mappedEntity.getDescription()).isEqualTo(learningPathDTO.description());
        assertThat(mappedEntity.getCreators()).isEqualTo(learningPathDTO.creators());
        assertThat(mappedEntity.getId()).isNull();
        assertThat(mappedEntity.getCourses()).isNull();
        assertThat(mappedEntity.getTags()).isNull();
        assertThat(mappedEntity.getNumberOfEnrolledUsers()).isEqualTo(0);
        assertThat(mappedEntity.getIconURL()).isEqualTo(learningPathDTO.iconURL());
        assertThat(mappedEntity.getCreatedAt()).isNotNull();
        assertThat(mappedEntity.getLastUpdatedAt()).isNull();
    }

    @Test
    public void testMapFullLearningPathEntityToDTO() {
        LearningPathEntity learningPathEntity = LearningPathTestUtil.createFullLearningPath();
        LearningPathDTO mappedDTO = testMapper.mapToDTO(learningPathEntity);
        assertThat(mappedDTO.id()).isEqualTo(learningPathEntity.getId());
        assertThat(mappedDTO.title()).isEqualTo(learningPathEntity.getTitle());
        assertThat(mappedDTO.description()).isEqualTo(learningPathEntity.getDescription());
        assertThat(mappedDTO.courseIds()).isEqualTo(learningPathEntity.getCourses().stream().map(CourseEntity::getId).collect(Collectors.toSet()));
        assertThat(mappedDTO.tags()).isEqualTo(learningPathEntity.getTags().stream().map(TagEntity::getTag).collect(Collectors.toSet()));
        assertThat(mappedDTO.creators()).isEqualTo(learningPathEntity.getCreators());
        assertThat(mappedDTO.numberOfEnrolledUsers()).isEqualTo(learningPathEntity.getNumberOfEnrolledUsers());
        assertThat(mappedDTO.iconURL()).isEqualTo(learningPathEntity.getIconURL());
        assertThat(mappedDTO.createdAt()).isEqualTo(learningPathEntity.getCreatedAt());
        assertThat(mappedDTO.lastUpdatedAt()).isEqualTo(learningPathEntity.getLastUpdatedAt());
    }

    @Test
    public void testMapFullLearningPathDTOToEntity() {
        LearningPathDTO learningPathDTO = LearningPathTestUtil.createFullLearningPathDTO();
        LearningPathEntity mappedEntity = testMapper.mapToEntity(learningPathDTO);
        assertThat(mappedEntity.getId()).isNull();
        assertThat(mappedEntity.getTitle()).isEqualTo(learningPathDTO.title());
        assertThat(mappedEntity.getDescription()).isEqualTo(learningPathDTO.description());
        assertThat(mappedEntity.getCourses()).isNull();
        assertThat(mappedEntity.getTags()).isNull();
        assertThat(mappedEntity.getCreators()).isEqualTo(learningPathDTO.creators());
        assertThat(mappedEntity.getNumberOfEnrolledUsers()).isEqualTo(0);
        assertThat(mappedEntity.getIconURL()).isEqualTo(learningPathDTO.iconURL());
        assertThat(mappedEntity.getCreatedAt()).isNotNull();
        assertThat(mappedEntity.getCreatedAt()).isInstanceOf(LocalDateTime.class);
        assertThat(mappedEntity.getLastUpdatedAt()).isEqualTo(learningPathDTO.lastUpdatedAt());
    }
}
