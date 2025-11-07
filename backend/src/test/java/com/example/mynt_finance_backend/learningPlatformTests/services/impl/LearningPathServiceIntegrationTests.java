package com.example.mynt_finance_backend.learningPlatformTests.services.impl;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.LearningPathDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.*;
import com.example.mynt_finance_backend.learningPlatform.domain.entityEnums.ContentDifficulty;
import com.example.mynt_finance_backend.learningPlatform.services.LearningPathService;
import com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries.TestContainerConfiguration;
import com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries.TestEntityRepositoryFacade;
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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LearningPathServiceIntegrationTests implements TestContainerConfiguration {

    @Autowired
    private LearningPathService testService;

    @Autowired
    private TestEntityRepositoryFacade testEntityRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testExists() {

        LearningPathEntity learningPath = LearningPathTestUtil.createEmptyLearningPath();
        testEntityRepository.saveLearningPath(learningPath);
        entityManager.flush();

        boolean exists = testService.exists(1);
        assertThat(exists).isTrue();
    }

    @Test
    public void testCreateLearningPath() {

        LearningPathDTO dto = LearningPathTestUtil.createEmptyLearningPathDTO();
        LearningPathDTO createdDTO = testService.createLearningPath(dto);
        entityManager.flush();

        LearningPathEntity createdEntity = testEntityRepository.findLearningPathById(1);

        assertThat(createdDTO).isNotNull();
        assertThat(createdEntity).isNotNull();
        assertThat(createdDTO.id()).isEqualTo(1);
        assertThat(createdDTO.id()).isEqualTo(createdEntity.getId());
        assertThat(createdDTO.title()).isEqualTo(dto.title());
        assertThat(createdDTO.title()).isEqualTo(createdEntity.getTitle());
        assertThat(createdDTO.description()).isEqualTo(dto.description());
        assertThat(createdDTO.description()).isEqualTo(createdEntity.getDescription());
        assertThat(createdDTO.creators()).isEqualTo(dto.creators());
        assertThat(createdDTO.creators()).isEqualTo(createdEntity.getCreators());
        assertThat(createdDTO.iconURL()).isEqualTo(dto.iconURL());
        assertThat(createdDTO.iconURL()).isEqualTo(createdEntity.getIconURL());
        assertThat(createdDTO.numberOfEnrolledUsers()).isEqualTo(0);
        assertThat(createdDTO.numberOfEnrolledUsers()).isEqualTo(createdEntity.getNumberOfEnrolledUsers());
        assertThat(createdDTO.createdAt().truncatedTo(ChronoUnit.SECONDS)).isEqualTo(createdEntity.getCreatedAt().truncatedTo(ChronoUnit.SECONDS));
        assertThat(createdDTO.lastUpdatedAt()).isNull();
        assertThat(createdEntity.getCourses()).isNull();
        assertThat(createdEntity.getTags()).isNull();
    }

    @Test
    public void testReadLearningPathById() {

        LearningPathEntity learningPath = LearningPathTestUtil.createConvertedLearningPath();
        testEntityRepository.saveCourses(learningPath.getCourses());
        testEntityRepository.saveTags(learningPath.getTags());
        testEntityRepository.saveLearningPath(learningPath);
        entityManager.flush();

        LearningPathDTO foundDTO = testService.readLearningPathById(1);

        assertThat(foundDTO).isNotNull();
        assertThat(foundDTO.title()).isEqualTo(learningPath.getTitle());
        assertThat(foundDTO.description()).isEqualTo(learningPath.getDescription());
        assertThat(foundDTO.courseIds()).isEqualTo(learningPath.getCourses().stream().map(CourseEntity::getId).collect(Collectors.toSet()));
        assertThat(foundDTO.tags()).isEqualTo(learningPath.getTags().stream().map(TagEntity::getTag).collect(Collectors.toSet()));
        assertThat(foundDTO.creators()).isEqualTo(learningPath.getCreators());
        assertThat(foundDTO.numberOfEnrolledUsers()).isEqualTo(learningPath.getNumberOfEnrolledUsers());
        assertThat(foundDTO.iconURL()).isEqualTo(learningPath.getIconURL());
        assertThat(foundDTO.lastUpdatedAt().truncatedTo(ChronoUnit.SECONDS)).isEqualTo(learningPath.getLastUpdatedAt().truncatedTo(ChronoUnit.SECONDS));
    }

    @Test
    public void testReadAllLearningPaths() {

        List<LearningPathDTO> noLearningPaths = testService.readAllLearningPaths();
        assertThat(noLearningPaths).isNotNull();
        assertThat(noLearningPaths).hasSize(0);

        Set<LearningPathEntity> learningPaths = LearningPathTestUtil.createLearningPathSet();
        testEntityRepository.saveLearningPaths(learningPaths);
        entityManager.flush();

        List<LearningPathDTO> foundDTOs = testService.readAllLearningPaths();
        assertThat(foundDTOs).isNotNull();
        assertThat(foundDTOs).hasSize(3);
    }

    @Test
    public void testFindAllLearningPathsById() {

        Set<LearningPathEntity> learningPaths = LearningPathTestUtil.createLearningPathSet();
        testEntityRepository.saveLearningPaths(learningPaths);
        entityManager.flush();

        Set<LearningPathEntity> foundEntities = testService.findAllLearningPathsById(Set.of(2, 3));
        Set<LearningPathEntity> allLearningPaths = testEntityRepository.findAllLearningPaths();

        assertThat(foundEntities).isNotNull();
        assertThat(foundEntities).isNotEmpty();
        assertThat(foundEntities).hasSize(2);
        foundEntities.forEach(learningPath -> assertThat(learningPath.getId()).isNotEqualTo(1));
        assertThat(allLearningPaths).isNotNull();
        assertThat(allLearningPaths).hasSize(3);
    }

    @Test
    public void testFindLearningPathById() {

        LearningPathEntity learningPath = LearningPathTestUtil.createConvertedLearningPath();
        testEntityRepository.saveCourses(learningPath.getCourses());
        testEntityRepository.saveTags(learningPath.getTags());
        testEntityRepository.saveLearningPath(learningPath);
        entityManager.flush();

        Optional<LearningPathEntity> foundEntity = testService.findLearningPathById(1);

        assertThat(foundEntity).isPresent();
        assertThat(foundEntity.get().getTitle()).isEqualTo(learningPath.getTitle());
        assertThat(foundEntity.get().getDescription()).isEqualTo(learningPath.getDescription());
        assertThat(foundEntity.get().getCourses()).isEqualTo(learningPath.getCourses());
        assertThat(foundEntity.get().getTags()).isEqualTo(learningPath.getTags());
        assertThat(foundEntity.get().getCreators()).isEqualTo(learningPath.getCreators());
        assertThat(foundEntity.get().getNumberOfEnrolledUsers()).isEqualTo(learningPath.getNumberOfEnrolledUsers());
        assertThat(foundEntity.get().getIconURL()).isEqualTo(learningPath.getIconURL());
        assertThat(foundEntity.get().getLastUpdatedAt().truncatedTo(ChronoUnit.SECONDS)).isEqualTo(learningPath.getLastUpdatedAt().truncatedTo(ChronoUnit.SECONDS));
    }

    @Test
    public void testUpdateLearningPath() {

        LearningPathEntity learningPath = LearningPathTestUtil.createConvertedLearningPath();
        testEntityRepository.saveCourses(learningPath.getCourses());
        testEntityRepository.saveTags(learningPath.getTags());
        testEntityRepository.saveLearningPath(learningPath);
        entityManager.flush();

        Set<Integer> courseSet = new HashSet<>();
        Collections.addAll(courseSet, 4, 5, 6);
        Set<String> tagSet = new HashSet<>();
        Collections.addAll(tagSet, "tag4", "tag5", "tag6");
        Set<String> creatorSet = new HashSet<>();
        Collections.addAll(creatorSet, "newCreator");

        LearningPathDTO dto = new LearningPathDTO(
                2,
                "newTitle", // should update
                "newDescription", // should update
                ContentDifficulty.INTERMEDIATE, // should update
                courseSet,
                tagSet,
                creatorSet, // should update
                100,
                "newIcon.url", // should update
                LocalDateTime.of(2025, 1, 1, 0, 0, 0),
                LocalDateTime.of(2025, 1, 1, 1, 0, 0)
        );

        LearningPathDTO updatedDTO = testService.updateLearningPath(dto, 1);
        entityManager.flush();

        LearningPathEntity updatedEntity = testEntityRepository.findLearningPathById(1);

        assertThat(updatedDTO).isNotNull();
        assertThat(updatedEntity).isNotNull();
        assertThat(updatedDTO.id()).isNotEqualTo(dto.id());
        assertThat(updatedDTO.title()).isEqualTo(dto.title());
        assertThat(updatedDTO.description()).isEqualTo(dto.description());
        assertThat(updatedDTO.courseIds()).isNotEqualTo(dto.courseIds());
        assertThat(dto.courseIds()).isNotEqualTo(updatedEntity.getCourses().stream().map(CourseEntity::getId).collect(Collectors.toSet()));
        assertThat(updatedDTO.tags()).isNotEqualTo(dto.tags());
        assertThat(dto.tags()).isNotEqualTo(updatedEntity.getTags().stream().map(TagEntity::getTag).collect(Collectors.toSet()));
        assertThat(updatedDTO.creators()).isEqualTo(updatedEntity.getCreators());
        assertThat(updatedDTO.numberOfEnrolledUsers()).isNotEqualTo(dto.numberOfEnrolledUsers());
        assertThat(updatedDTO.iconURL()).isEqualTo(dto.iconURL());
        assertThat(updatedDTO.createdAt()).isNotEqualTo(dto.createdAt());
        assertThat(updatedDTO.lastUpdatedAt().truncatedTo(ChronoUnit.SECONDS)).isNotEqualTo(dto.lastUpdatedAt().truncatedTo(ChronoUnit.SECONDS));
    }

    @Test
    public void testDeleteLearningPathById() {

        LearningPathEntity learningPath = LearningPathTestUtil.createConvertedLearningPath();
        testEntityRepository.saveCourses(learningPath.getCourses());
        testEntityRepository.saveTags(learningPath.getTags());
        testEntityRepository.saveLearningPath(learningPath);
        entityManager.flush();
        entityManager.clear();

        testService.deleteLearningPath(1);
        entityManager.flush();

        LearningPathEntity foundLearningPath = testEntityRepository.findLearningPathById(1);
        Set<CourseEntity> courses = testEntityRepository.findAllCourses();
        Set<TagEntity> tags = testEntityRepository.findAllTags();

        assertThat(foundLearningPath).isNull();
        assertThat(courses).isNotNull();
        assertThat(courses).isNotEmpty();
        assertThat(courses).hasSize(3);
        assertThat(tags).isNotNull();
        assertThat(tags).isNotEmpty();
        assertThat(tags).hasSize(3);

        tags.stream().flatMap(tag -> tag.getLearningPaths().stream())
                .forEach(learningPathEntity -> assertThat(learningPathEntity.getId()).isNotEqualTo(1));
    }

    @Test
    public void testAddCourse() {

        CourseEntity course = CourseTestUtil.createEmptyCourse();
        testEntityRepository.saveCourse(course);
        entityManager.flush();

        LearningPathEntity learningPath = LearningPathTestUtil.createEmptyLearningPath();
        testEntityRepository.saveLearningPath(learningPath);
        entityManager.flush();
        entityManager.clear();

        LearningPathDTO updatedDTO = testService.addCourse(1, 1);
        entityManager.flush();

        LearningPathEntity updatedEntity = testEntityRepository.findLearningPathById(1);
        CourseEntity updatedCourse = testEntityRepository.findCourseById(1);

        assertThat(updatedDTO.id()).isEqualTo(updatedEntity.getId());
        assertThat(updatedDTO.courseIds()).contains(updatedCourse.getId());
        assertThat(updatedEntity.getCourses()).contains(updatedCourse);
    }

    @Test
    public void testRemoveCourse() {

        CourseEntity course = CourseTestUtil.createEmptyCourse();
        testEntityRepository.saveCourse(course);
        entityManager.flush();

        LearningPathEntity learningPath = LearningPathTestUtil.createEmptyLearningPath();
        testEntityRepository.saveLearningPath(learningPath);
        entityManager.flush();

        learningPath.addCourse(course);
        testEntityRepository.saveLearningPath(learningPath);
        entityManager.flush();
        entityManager.clear();

        LearningPathDTO updatedDTO = testService.removeCourse(1, 1);
        entityManager.flush();

        LearningPathEntity updatedEntity = testEntityRepository.findLearningPathById(1);
        CourseEntity updatedCourse = testEntityRepository.findCourseById(1);

        assertThat(updatedDTO.id()).isEqualTo(updatedEntity.getId());
        assertThat(updatedCourse).isNotNull();
        assertThat(updatedDTO.courseIds()).doesNotContain(updatedCourse.getId());
        assertThat(updatedEntity.getCourses()).doesNotContain(updatedCourse);
    }

    @Test
    public void testAddTag() {

        TagEntity tag = TagTestUtil.createEmptyTag();
        testEntityRepository.saveTag(tag);
        entityManager.flush();

        LearningPathEntity learningPath = LearningPathTestUtil.createEmptyLearningPath();
        testEntityRepository.saveLearningPath(learningPath);
        entityManager.flush();
        entityManager.clear();

        LearningPathDTO updatedDTO = testService.addTag(1, 1);
        entityManager.flush();

        LearningPathEntity updatedEntity = testEntityRepository.findLearningPathById(1);
        TagEntity updatedTag = testEntityRepository.findTagById(1);

        assertThat(updatedDTO.id()).isEqualTo(updatedEntity.getId());
        assertThat(updatedDTO.tags()).contains(updatedTag.getTag());
        assertThat(updatedEntity.getTags()).contains(updatedTag);
        assertThat(updatedTag.getLearningPaths()).contains(updatedEntity);
    }

    @Test
    public void removeAddTag() {

        TagEntity tag = TagTestUtil.createEmptyTag();
        testEntityRepository.saveTag(tag);
        entityManager.flush();

        LearningPathEntity learningPath = LearningPathTestUtil.createEmptyLearningPath();
        testEntityRepository.saveLearningPath(learningPath);
        entityManager.flush();

        learningPath.addTag(tag);
        tag.addLearningPath(learningPath);
        testEntityRepository.saveTag(tag);
        testEntityRepository.saveLearningPath(learningPath);
        entityManager.flush();
        entityManager.clear();

        LearningPathDTO updatedDTO = testService.removeTag(1, 1);
        entityManager.flush();

        LearningPathEntity updatedEntity = testEntityRepository.findLearningPathById(1);
        TagEntity updatedTag = testEntityRepository.findTagById(1);

        assertThat(updatedDTO.id()).isEqualTo(updatedEntity.getId());
        assertThat(updatedTag).isNotNull();
        assertThat(updatedDTO.tags()).doesNotContain(updatedTag.getTag());
        assertThat(updatedEntity.getTags()).doesNotContain(updatedTag);
        assertThat(updatedTag.getLearningPaths()).doesNotContain(updatedEntity);
    }

    @Test
    public void testHasCourse() {

        CourseEntity course = CourseTestUtil.createEmptyCourse();
        testEntityRepository.saveCourse(course);
        entityManager.flush();

        LearningPathEntity learningPath = LearningPathTestUtil.createEmptyLearningPath();
        testEntityRepository.saveLearningPath(learningPath);
        entityManager.flush();

        boolean learningPathExists = testService.exists(1);
        boolean noCourse = testService.hasCourse(1, 1);
        assertThat(learningPathExists).isTrue();
        assertThat(noCourse).isFalse();

        learningPath.addCourse(course);
        testEntityRepository.saveLearningPath(learningPath);
        entityManager.flush();
        entityManager.clear();

        boolean hasCourse1 = testService.hasCourse(1, 1);
        boolean hasCourse2 = testService.hasCourse(1, 2);

        assertThat(hasCourse1).isTrue();
        assertThat(hasCourse2).isFalse();
    }

    @Test
    public void testHasTag() {

        TagEntity tag = TagTestUtil.createEmptyTag();
        testEntityRepository.saveTag(tag);
        entityManager.flush();

        LearningPathEntity learningPath = LearningPathTestUtil.createEmptyLearningPath();
        testEntityRepository.saveLearningPath(learningPath);
        entityManager.flush();

        boolean learningPathExists = testService.exists(1);
        boolean noTag = testService.hasTag(1, 1);
        assertThat(learningPathExists).isTrue();
        assertThat(noTag).isFalse();

        learningPath.addTag(tag);
        tag.addLearningPath(learningPath);
        testEntityRepository.saveTag(tag);
        testEntityRepository.saveLearningPath(learningPath);
        entityManager.flush();
        entityManager.clear();

        boolean hasTag1 = testService.hasTag(1, 1);
        boolean hasTag2 = testService.hasTag(1, 2);

        assertThat(hasTag1).isTrue();
        assertThat(hasTag2).isFalse();
    }
}
