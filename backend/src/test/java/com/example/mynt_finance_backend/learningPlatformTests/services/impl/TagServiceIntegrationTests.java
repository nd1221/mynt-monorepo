package com.example.mynt_finance_backend.learningPlatformTests.services.impl;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.TagDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.CourseEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.LearningPathEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TagEntity;
import com.example.mynt_finance_backend.learningPlatform.services.TagService;
import com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries.TestContainerConfiguration;
import com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries.TestEntityRepositoryFacade;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.CommonTestUtils;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.CourseTestUtil;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.LearningPathTestUtil;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.TagTestUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TagServiceIntegrationTests implements TestContainerConfiguration {

    @Autowired
    private TagService testService;

    @Autowired
    private TestEntityRepositoryFacade testEntityRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testExists() {

        TagEntity tag = TagTestUtil.createEmptyTag();
        testEntityRepository.saveTag(tag);
        entityManager.flush();

        boolean exists = testService.exists(1);
        assertThat(exists).isTrue();
    }

    @Test
    public void testCreateTag() {

        TagDTO dto = TagTestUtil.createFullTagDTO();
        TagDTO createdDTO = testService.createTag(dto);
        entityManager.flush();

        TagEntity createdEntity = testEntityRepository.findTagById(1);

        assertThat(createdDTO).isNotNull();
        assertThat(createdEntity).isNotNull();
        assertThat(createdDTO.id()).isEqualTo(1);
        assertThat(createdDTO.id()).isNotEqualTo(dto.id());
        assertThat(createdDTO.id()).isEqualTo(createdEntity.getId());
        assertThat(createdDTO.tag()).isEqualTo(createdEntity.getTag());
        assertThat(createdDTO.tag()).isEqualTo(dto.tag());
        assertThat(createdDTO.learningPathIds()).isEmpty();
        assertThat(createdDTO.courseIds()).isEmpty();
        assertThat(createdEntity.getCourses()).isNull();
        assertThat(createdEntity.getLearningPaths()).isNull();
    }

    @Test
    public void testReadTagById() {

        Set<LearningPathEntity> learningPaths = LearningPathTestUtil.createLearningPathSet();
        testEntityRepository.saveLearningPaths(learningPaths);
        entityManager.flush();

        Set<CourseEntity> courses  = CourseTestUtil.createCourseSet();
        testEntityRepository.saveCourses(courses);
        entityManager.flush();

        TagEntity tag = TagTestUtil.createEmptyTag();
        testEntityRepository.saveTag(tag);
        entityManager.flush();

        learningPaths.forEach(lp -> lp.setTags(CommonTestUtils.toSet(tag)));
        tag.setLearningPaths(learningPaths);
        courses.forEach(c -> c.setTags(CommonTestUtils.toSet(tag)));
        tag.setCourses(courses);

        testEntityRepository.saveLearningPaths(learningPaths);
        testEntityRepository.saveCourses(courses);
        testEntityRepository.saveTag(tag);
        entityManager.flush();

        TagDTO foundDTO = testService.readTagById(1);

        assertThat(foundDTO).isNotNull();
        assertThat(foundDTO.id()).isEqualTo(1);
        assertThat(foundDTO.id()).isEqualTo(tag.getId());
        assertThat(foundDTO.tag()).isEqualTo(tag.getTag());
        assertThat(foundDTO.learningPathIds()).isEqualTo(tag.getLearningPaths().stream().map(LearningPathEntity::getId).collect(Collectors.toSet()));
        assertThat(foundDTO.courseIds()).isEqualTo(tag.getCourses().stream().map(CourseEntity::getId).collect(Collectors.toSet()));
    }

    @Test
    public void testReadAllTags() {

        Set<LearningPathEntity> learningPaths = LearningPathTestUtil.createLearningPathSet();
        testEntityRepository.saveLearningPaths(learningPaths);
        entityManager.flush();

        Set<CourseEntity> courses  = CourseTestUtil.createCourseSet();
        testEntityRepository.saveCourses(courses);
        entityManager.flush();

        TagEntity tag1 = TagTestUtil.createEmptyTag();
        testEntityRepository.saveTag(tag1);
        entityManager.flush();

        TagEntity tag2 = TagTestUtil.createEmptyTag();
        testEntityRepository.saveTag(tag2);
        entityManager.flush();

        learningPaths.forEach(lp -> lp.setTags(CommonTestUtils.toSet(tag1, tag2)));
        tag1.setLearningPaths(learningPaths);
        tag2.setLearningPaths(learningPaths);
        courses.forEach(c -> c.setTags(CommonTestUtils.toSet(tag1, tag2)));
        tag1.setCourses(courses);
        tag2.setCourses(courses);

        testEntityRepository.saveLearningPaths(learningPaths);
        testEntityRepository.saveCourses(courses);
        testEntityRepository.saveTag(tag1);
        testEntityRepository.saveTag(tag2);
        entityManager.flush();

        List<TagDTO> foundTags = testService.readAllTags();

        assertThat(foundTags).isNotNull();
        assertThat(foundTags).isNotEmpty();
        assertThat(foundTags).hasSize(2);
        assertThat(foundTags.get(0)).isNotEqualTo(foundTags.get(1));

        assertThat(foundTags.get(0).id()).isEqualTo(tag1.getId());
        assertThat(foundTags.get(0).tag()).isEqualTo(tag1.getTag());
        assertThat(foundTags.get(0).learningPathIds()).isEqualTo(tag1.getLearningPaths().stream().map(LearningPathEntity::getId).collect(Collectors.toSet()));
        assertThat(foundTags.get(0).courseIds()).isEqualTo(tag1.getCourses().stream().map(CourseEntity::getId).collect(Collectors.toSet()));
    }

    // FindTagById

    @Test
    public void testFindAllTagsById() {

        Set<TagEntity> tags = TagTestUtil.createTagSet();
        testEntityRepository.saveTags(tags);
        entityManager.flush();

        Set<TagEntity> foundTags = testService.findAllTagsById(CommonTestUtils.toSet(1, 2));

        assertThat(foundTags).isNotNull();
        assertThat(foundTags).isNotEmpty();
        assertThat(foundTags).hasSize(2);

        foundTags.forEach(t -> assertThat(t.getId()).isNotEqualTo(3));
    }

    @Test
    public void testUpdateTag() {

        Set<LearningPathEntity> learningPaths = LearningPathTestUtil.createLearningPathSet();
        testEntityRepository.saveLearningPaths(learningPaths);
        entityManager.flush();

        Set<CourseEntity> courses  = CourseTestUtil.createCourseSet();
        testEntityRepository.saveCourses(courses);
        entityManager.flush();

        TagEntity tag = TagTestUtil.createEmptyTag();
        testEntityRepository.saveTag(tag);
        entityManager.flush();

        learningPaths.forEach(lp -> lp.setTags(CommonTestUtils.toSet(tag)));
        tag.setLearningPaths(learningPaths);
        courses.forEach(c -> c.setTags(CommonTestUtils.toSet(tag)));
        tag.setCourses(courses);

        testEntityRepository.saveLearningPaths(learningPaths);
        testEntityRepository.saveCourses(courses);
        testEntityRepository.saveTag(tag);
        entityManager.flush();

        TagDTO dto = new TagDTO(
                5,
                "newTag",
                CommonTestUtils.getUpdatedIntegerIds(),
                CommonTestUtils.getUpdatedIntegerIds()
        );

        TagDTO updatedDTO = testService.updateTagById(1, dto);
        entityManager.flush();

        TagEntity updatedEntity = testEntityRepository.findTagById(1);

        assertThat(updatedDTO).isNotNull();
        assertThat(updatedEntity).isNotNull();
        assertThat(updatedDTO.id()).isEqualTo(1);
        assertThat(updatedDTO.id()).isEqualTo(updatedEntity.getId());
        assertThat(updatedDTO.id()).isNotEqualTo(dto.id());
        assertThat(updatedDTO.tag()).isEqualTo(updatedEntity.getTag());
        assertThat(updatedDTO.tag()).isEqualTo(dto.tag());
        assertThat(updatedDTO.learningPathIds()).isEqualTo(updatedEntity.getLearningPaths().stream().map(LearningPathEntity::getId).collect(Collectors.toSet()));
        assertThat(updatedDTO.learningPathIds()).isNotEqualTo(dto.learningPathIds());
        assertThat(updatedDTO.courseIds()).isEqualTo(updatedEntity.getCourses().stream().map(CourseEntity::getId).collect(Collectors.toSet()));
        assertThat(updatedDTO.courseIds()).isNotEqualTo(dto.courseIds());
    }

    @Test
    public void testDeleteTagById() {

        Set<LearningPathEntity> learningPaths = LearningPathTestUtil.createLearningPathSet();
        testEntityRepository.saveLearningPaths(learningPaths);
        entityManager.flush();

        Set<CourseEntity> courses  = CourseTestUtil.createCourseSet();
        testEntityRepository.saveCourses(courses);
        entityManager.flush();

        TagEntity tag = TagTestUtil.createEmptyTag();
        testEntityRepository.saveTag(tag);
        entityManager.flush();

        learningPaths.forEach(lp -> lp.setTags(CommonTestUtils.toSet(tag)));
        tag.setLearningPaths(learningPaths);
        courses.forEach(c -> c.setTags(CommonTestUtils.toSet(tag)));
        tag.setCourses(courses);

        testEntityRepository.saveLearningPaths(learningPaths);
        testEntityRepository.saveCourses(courses);
        testEntityRepository.saveTag(tag);
        entityManager.flush();

        testService.deleteTagById(1);
        entityManager.flush();
        entityManager.clear();

        TagEntity foundTag = testEntityRepository.findTagById(1);
        Set<LearningPathEntity> foundLearningPaths = testEntityRepository.findAllLearningPaths();
        Set<CourseEntity> foundCourses = testEntityRepository.findAllCourses();

        assertThat(foundTag).isNull();
        assertThat(foundLearningPaths).isNotNull();
        assertThat(foundCourses).isNotNull();

        foundLearningPaths.stream().flatMap(lp -> lp.getTags().stream()).toList()
                .forEach(t -> assertThat(t.getId()).isNotEqualTo(1));
        foundCourses.stream().flatMap(c -> c.getTags().stream()).toList()
                .forEach(t -> assertThat(t.getId()).isNotEqualTo(1));
    }
}
