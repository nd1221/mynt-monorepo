package com.example.mynt_finance_backend.learningPlatformTests.mappers;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.TagDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.CourseEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.LearningPathEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TagEntity;
import com.example.mynt_finance_backend.learningPlatform.mappers.Impl.TagMapperImpl;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.TagTestUtil;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class TagMapperUnitTests {

    private final Mapper<TagDTO, TagEntity> testMapper;

    public TagMapperUnitTests() {
        this.testMapper = new TagMapperImpl();
    }

    @Test
    public void testMapEmptyTagEntityToDTO() {
        TagEntity tagEntity = TagTestUtil.createEmptyTag();
        TagDTO mappedDTO = testMapper.mapToDTO(tagEntity);
        assertThat(mappedDTO.tag()).isEqualTo(tagEntity.getTag());
        assertThat(mappedDTO.id()).isNull();
        assertThat(mappedDTO.learningPathIds()).isEmpty();
        assertThat(mappedDTO.courseIds()).isEmpty();
    }

    @Test
    public void testMapEmptyTagDTOToEntity() {
        TagDTO tagDTO = TagTestUtil.createEmptyTagDTO();
        TagEntity mappedEntity = testMapper.mapToEntity(tagDTO);
        assertThat(mappedEntity.getTag()).isEqualTo(tagDTO.tag());
        assertThat(mappedEntity.getId()).isNull();
        assertThat(mappedEntity.getLearningPaths()).isNull();
        assertThat(mappedEntity.getCourses()).isNull();
    }

    @Test
    public void testMapFullTagEntityToDTO() {
        TagEntity tagEntity = TagTestUtil.createFullTag();
        TagDTO mappedDTO = testMapper.mapToDTO(tagEntity);
        assertThat(mappedDTO.id()).isEqualTo(tagEntity.getId());
        assertThat(mappedDTO.tag()).isEqualTo(tagEntity.getTag());
        assertThat(mappedDTO.learningPathIds()).isEqualTo(tagEntity.getLearningPaths().stream().map(LearningPathEntity::getId).collect(Collectors.toSet()));
        assertThat(mappedDTO.courseIds()).isEqualTo(tagEntity.getCourses().stream().map(CourseEntity::getId).collect(Collectors.toSet()));
    }

    @Test
    public void testMapFullTagDTOToEntity() {
        TagDTO tagDTO = TagTestUtil.createFullTagDTO();
        TagEntity mappedEntity = testMapper.mapToEntity(tagDTO);
        assertThat(mappedEntity.getId()).isNull();
        assertThat(mappedEntity.getTag()).isEqualTo(tagDTO.tag());
        assertThat(mappedEntity.getLearningPaths()).isNull();
        assertThat(mappedEntity.getCourses()).isNull();
    }
}
