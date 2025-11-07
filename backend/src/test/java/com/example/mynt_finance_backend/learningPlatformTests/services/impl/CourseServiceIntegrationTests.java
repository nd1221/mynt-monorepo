package com.example.mynt_finance_backend.learningPlatformTests.services.impl;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.CourseDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.*;
import com.example.mynt_finance_backend.learningPlatform.domain.entityEnums.ContentDifficulty;
import com.example.mynt_finance_backend.learningPlatform.services.CourseService;
import com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries.TestContainerConfiguration;
import com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries.TestEntityRepositoryFacade;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.CourseTestUtil;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.SectionTestUtil;
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
public class CourseServiceIntegrationTests implements TestContainerConfiguration {

    @Autowired
    private CourseService testService;

    @Autowired
    private TestEntityRepositoryFacade testEntityRepository;


    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testExists() {

        CourseEntity course = CourseTestUtil.createConvertedCourse();
        testEntityRepository.saveCourse(course);
        entityManager.flush();

        boolean exists = testService.exists(1);
        assertThat(exists).isTrue();
    }

//    @Test
//    public void testCreateCourse() {
//
//        CourseDTO dto = CourseTestUtil.createEmptyCourseDTO();
//        CourseDTO savedDTO = testService.createCourse(dto);
//        entityManager.flush();
//
//        CourseEntity savedEntity = testEntityRepository.findCourseById(1);
//
//        assertThat(savedDTO).isNotNull();
//        assertThat(savedDTO.title()).isEqualTo(dto.title());
//        assertThat(savedDTO.description()).isEqualTo(dto.description());
//        assertThat(savedDTO.difficulty()).isEqualTo(dto.difficulty());
//        assertThat(savedDTO.creators()).isEqualTo(dto.creators());
//        assertThat(savedDTO.iconURL()).isEqualTo(dto.iconURL());
//        assertThat(savedDTO.createdAt()).isNotNull();
//        assertThat(savedDTO.numberOfEnrolledUsers()).isEqualTo(0);
//        assertThat(savedDTO.sectionIds()).isEmpty();
//        assertThat(savedDTO.tags()).isEmpty();
//        assertThat(savedEntity).isNotNull();
//        assertThat(savedEntity.getId()).isEqualTo(savedDTO.id());
//        assertThat(savedEntity.getTitle()).isEqualTo(savedDTO.title());
//        assertThat(savedEntity.getDescription()).isEqualTo(savedDTO.description());
//        assertThat(savedEntity.getDifficulty()).isEqualTo(savedDTO.difficulty());
//        assertThat(savedEntity.getCreators()).isEqualTo(savedDTO.creators());
//        assertThat(savedEntity.getNumberOfEnrolledUsers()).isEqualTo(savedDTO.numberOfEnrolledUsers());
//        assertThat(savedEntity.getIconURL()).isEqualTo(savedDTO.iconURL());
//        assertThat(savedEntity.getCreatedAt().truncatedTo(ChronoUnit.SECONDS)).isEqualTo(savedDTO.createdAt().truncatedTo(ChronoUnit.SECONDS));
//        assertThat(savedEntity.getSections()).isNull();
//        assertThat(savedEntity.getTags()).isNull();
//        assertThat(savedEntity.getLastUpdatedAt()).isNull();
//    }

//    @Test
//    public void testReadCourseById() {
//
//        CourseEntity course = CourseTestUtil.createConvertedCourse();
//        testEntityRepository.saveSections(course.getSections());
//        testEntityRepository.saveTags(course.getTags());
//        testEntityRepository.saveCourse(course);
//        entityManager.flush();
//
//        CourseDTO foundDTO = testService.readCourseById(1);
//
//        assertThat(foundDTO).isNotNull();
//        assertThat(foundDTO.id()).isEqualTo(course.getId());
//        assertThat(foundDTO.title()).isEqualTo(course.getTitle());
//        assertThat(foundDTO.description()).isEqualTo(course.getDescription());
//        assertThat(foundDTO.difficulty()).isEqualTo(course.getDifficulty());
//        assertThat(foundDTO.creators()).isEqualTo(course.getCreators());
//        assertThat(foundDTO.numberOfEnrolledUsers()).isEqualTo(course.getNumberOfEnrolledUsers());
//        assertThat(foundDTO.iconURL()).isEqualTo(course.getIconURL());
//        assertThat(foundDTO.lastUpdatedAt().truncatedTo(ChronoUnit.SECONDS)).isEqualTo(course.getLastUpdatedAt().truncatedTo(ChronoUnit.SECONDS));
//        assertThat(foundDTO.sectionIds()).isEqualTo(course.getSections().stream().map(SectionEntity::getId).collect(Collectors.toSet()));
//        assertThat(foundDTO.tags()).isEqualTo(course.getTags().stream().map(TagEntity::getTag).collect(Collectors.toSet()));
//    }

//    @Test
//    public void testReadAllCourses() {
//
//        List<CourseDTO> noCourses = testService.readAllCourses();
//        assertThat(noCourses).isNotNull();
//        assertThat(noCourses).hasSize(0);
//
//        Set<CourseEntity> courses = CourseTestUtil.createCourseSet();
//        testEntityRepository.saveCourses(courses);
//        entityManager.flush();
//
//        List<CourseDTO> foundCourses = testService.readAllCourses();
//
//        assertThat(foundCourses).isNotNull();
//        assertThat(foundCourses).hasSize(3);
//    }

    @Test
    public void testFindAllCoursesById() {

        Set<CourseEntity> courses = CourseTestUtil.createCourseSet();
        testEntityRepository.saveCourses(courses);
        entityManager.flush();

        Set<CourseEntity> foundCourses = testService.findAllCoursesById(Set.of(1, 3));
        Set<CourseEntity> allCourses = testEntityRepository.findAllCourses();

        assertThat(foundCourses).isNotNull();
        assertThat(foundCourses).isNotEmpty();
        assertThat(foundCourses).hasSize(2);
        foundCourses.forEach(course -> assertThat(course.getId() != 2).isTrue());
        assertThat(allCourses).isNotNull();
        assertThat(allCourses).hasSize(3);
    }

    @Test
    public void testFindCourseById() {

        CourseEntity course = CourseTestUtil.createConvertedCourse();
        testEntityRepository.saveSections(course.getSections());
        testEntityRepository.saveTags(course.getTags());
        testEntityRepository.saveCourse(course);
        entityManager.flush();

        Optional<CourseEntity> foundEntity = testService.findCourseById(1);

        assertThat(foundEntity).isPresent();
        assertThat(foundEntity.get().getId()).isEqualTo(course.getId());
        assertThat(foundEntity.get().getTitle()).isEqualTo(course.getTitle());
        assertThat(foundEntity.get().getDescription()).isEqualTo(course.getDescription());
        assertThat(foundEntity.get().getDifficulty()).isEqualTo(course.getDifficulty());
        assertThat(foundEntity.get().getCreators()).isEqualTo(course.getCreators());
        assertThat(foundEntity.get().getNumberOfEnrolledUsers()).isEqualTo(course.getNumberOfEnrolledUsers());
        assertThat(foundEntity.get().getIconURL()).isEqualTo(course.getIconURL());
        assertThat(foundEntity.get().getLastUpdatedAt()).isEqualTo(course.getLastUpdatedAt());
        assertThat(foundEntity.get().getSections()).isEqualTo(course.getSections());
        assertThat(foundEntity.get().getTags()).isEqualTo(course.getTags());
    }

//    @Test
//    public void testUpdateCourse() {
//
//        CourseEntity course = CourseTestUtil.createConvertedCourse();
//        testEntityRepository.saveSections(course.getSections());
//        testEntityRepository.saveTags(course.getTags());
//        testEntityRepository.saveCourse(course);
//        entityManager.flush();
//
//        Set<Integer> sectionSet = new HashSet<>();
//        Collections.addAll(sectionSet, 4, 5, 6);
//        Set<String> tagSet = new HashSet<>();
//        Collections.addAll(tagSet, "tag4", "tag5", "tag6");
//        Set<String> creatorSet = new HashSet<>();
//        Collections.addAll(creatorSet, "newCreator");
//
//        CourseDTO dto = new CourseDTO(
//                3,
//                "newTitle", // should update
//                "newDescription", // should update
//                ContentDifficulty.ADVANCED, // should update
//                sectionSet,
//                tagSet,
//                creatorSet,
//                100,
//                "newIcon.url", // should update
//                LocalDateTime.of(2025, 1, 1, 1, 1, 0),
//                LocalDateTime.of(2025, 1, 1, 2, 1, 0),
//                null,
//                null,
//                1000
//        );
//
//        CourseDTO updatedDTO = testService.updateCourse(dto, 1);
//        entityManager.flush();
//
//        CourseEntity updatedEntity = testEntityRepository.findCourseById(1);
//
//        assertThat(updatedDTO).isNotNull();
//        assertThat(updatedEntity).isNotNull();
//        assertThat(updatedDTO.id()).isEqualTo(1);
//        assertThat(updatedDTO.id()).isNotEqualTo(dto.id());
//        assertThat(updatedDTO.title()).isEqualTo(dto.title());
//        assertThat(updatedDTO.description()).isEqualTo(dto.description());
//        assertThat(updatedDTO.difficulty()).isEqualTo(dto.difficulty());
//        assertThat(updatedDTO.sectionIds()).isNotEqualTo(dto.sectionIds());
//        assertThat(dto.sectionIds()).isNotEqualTo(updatedEntity.getSections().stream().map(SectionEntity::getId).collect(Collectors.toSet()));
//        assertThat(updatedDTO.tags()).isNotEqualTo(dto.tags());
//        assertThat(dto.tags()).isNotEqualTo(updatedEntity.getTags().stream().map(TagEntity::getTag).collect(Collectors.toSet()));
//        assertThat(updatedDTO.creators()).isEqualTo(dto.creators());
//        assertThat(updatedDTO.numberOfEnrolledUsers()).isNotEqualTo(dto.numberOfEnrolledUsers());
//        assertThat(updatedDTO.numberOfEnrolledUsers()).isEqualTo(updatedEntity.getNumberOfEnrolledUsers());
//        assertThat(updatedDTO.iconURL()).isEqualTo(dto.iconURL());
//        assertThat(updatedDTO.lastUpdatedAt().truncatedTo(ChronoUnit.SECONDS)).isNotEqualTo(dto.lastUpdatedAt().truncatedTo(ChronoUnit.SECONDS));
//    }

    @Test
    public void testDeleteCourseById() {

        CourseEntity course = CourseTestUtil.createConvertedCourse();
        testEntityRepository.saveSections(course.getSections());
        course.getSections().forEach(c -> c.setCourse(course));
        testEntityRepository.saveTags(course.getTags());
        course.getTags().forEach(t -> t.addCourse(course));
        testEntityRepository.saveCourse(course);
        entityManager.flush();
        entityManager.clear();

        testService.deleteCourseById(1);

        CourseEntity foundCourse = testEntityRepository.findCourseById(1);
        Set<SectionEntity> sections = testEntityRepository.findAllSections();
        Set<TagEntity> tags = testEntityRepository.findAllTags();

        assertThat(foundCourse).isNull();
        assertThat(sections).isEmpty();
        assertThat(tags).isNotNull();
        assertThat(tags).isNotEmpty();
        assertThat(tags).hasSize(3);

        tags.stream().flatMap(tag -> tag.getCourses().stream())
                .forEach(tagCourse -> assertThat(tagCourse.getId()).isNotEqualTo(1));
    }

    @Test
    public void testAddSection() {

        SectionEntity section = SectionTestUtil.createEmptySection();
        testEntityRepository.saveSection(section);
        entityManager.flush();

        CourseEntity course = CourseTestUtil.createEmptyCourse();
        testEntityRepository.saveCourse(course);
        entityManager.flush();
        entityManager.clear();

        CourseDTO updatedDTO = testService.addSection(1, 1);
        entityManager.flush();

        CourseEntity updatedEntity = testEntityRepository.findCourseById(1);
        SectionEntity updatedSection = testEntityRepository.findSectionById(1);

        assertThat(updatedDTO.id()).isEqualTo(updatedEntity.getId());
        assertThat(updatedDTO.sectionIds()).contains(updatedSection.getId());
        assertThat(updatedEntity.getSections()).contains(updatedSection);
        assertThat(updatedSection.getCourse()).isEqualTo(updatedEntity);
    }

    @Test
    public void testRemoveSection() {

        SectionEntity section = SectionTestUtil.createEmptySection();
        testEntityRepository.saveSection(section);
        entityManager.flush();

        CourseEntity course = CourseTestUtil.createEmptyCourse();
        testEntityRepository.saveCourse(course);
        entityManager.flush();

        course.addSection(section);
        section.setCourse(course);
        testEntityRepository.saveSection(section);
        testEntityRepository.saveCourse(course);
        entityManager.flush();

        CourseDTO updatedDTO = testService.removeSection(1, 1);
        entityManager.flush();

        CourseEntity updatedEntity = testEntityRepository.findCourseById(1);
        SectionEntity foundSection = testEntityRepository.findSectionById(1);

        assertThat(updatedDTO.id()).isEqualTo(updatedEntity.getId());
        assertThat(updatedDTO.sectionIds()).doesNotContain(section.getId());
        assertThat(updatedEntity.getSections()).doesNotContain(section);
        assertThat(foundSection).isNull();
    }

    @Test
    public void testAddTag() {

        TagEntity tag = TagTestUtil.createEmptyTag();
        testEntityRepository.saveTag(tag);
        entityManager.flush();;

        CourseEntity course = CourseTestUtil.createEmptyCourse();
        testEntityRepository.saveCourse(course);
        entityManager.flush();
        entityManager.clear();

        CourseDTO updatedDTO = testService.addTag(1, 1);
        entityManager.flush();

        CourseEntity updatedEntity = testEntityRepository.findCourseById(1);
        TagEntity updatedTag = testEntityRepository.findTagById(1);

        assertThat(updatedDTO.id()).isEqualTo(updatedEntity.getId());
        assertThat(updatedDTO.tags()).contains(updatedTag.getTag());
        assertThat(updatedEntity.getTags()).contains(updatedTag);
        assertThat(updatedTag.getCourses()).contains(updatedEntity);
    }

    @Test
    public void testRemoveTag() {

        TagEntity tag = TagTestUtil.createEmptyTag();
        testEntityRepository.saveTag(tag);
        entityManager.flush();;

        CourseEntity course = CourseTestUtil.createEmptyCourse();
        testEntityRepository.saveCourse(course);
        entityManager.flush();

        course.addTag(tag);
        tag.addCourse(course);
        testEntityRepository.saveTag(tag);
        testEntityRepository.saveCourse(course);
        entityManager.flush();
        entityManager.clear();

        CourseDTO updatedDTO = testService.removeTag(1, 1);
        entityManager.flush();

        CourseEntity updatedEntity = testEntityRepository.findCourseById(1);
        TagEntity foundTag = testEntityRepository.findTagById(1);

        assertThat(updatedDTO.id()).isEqualTo(updatedEntity.getId());
        assertThat(foundTag).isNotNull();
        assertThat(updatedDTO.tags()).doesNotContain(foundTag.getTag());
        assertThat(updatedEntity.getTags()).doesNotContain(foundTag);
        assertThat(foundTag.getCourses()).doesNotContain(updatedEntity);
    }

    @Test
    public void testHasSection() {

        SectionEntity section = SectionTestUtil.createEmptySection();
        testEntityRepository.saveSection(section);
        entityManager.flush();

        CourseEntity course = CourseTestUtil.createEmptyCourse();
        testEntityRepository.saveCourse(course);
        entityManager.flush();

        boolean courseExists = testService.exists(1);
        boolean noSection = testService.hasSection(1, 1);
        assertThat(courseExists).isTrue();
        assertThat(noSection).isFalse();

        course.addSection(section);
        section.setCourse(course);
        testEntityRepository.saveSection(section);
        testEntityRepository.saveCourse(course);
        entityManager.flush();
        entityManager.clear();

        boolean hasSection1 = testService.hasSection(1, 1);
        boolean hasSection2 = testService.hasSection(1, 2);

        assertThat(hasSection1).isTrue();
        assertThat(hasSection2).isFalse();
    }

    @Test
    public void testHasTag() {

        TagEntity tag = TagTestUtil.createEmptyTag();
        testEntityRepository.saveTag(tag);
        entityManager.flush();

        CourseEntity course = CourseTestUtil.createEmptyCourse();
        testEntityRepository.saveCourse(course);
        entityManager.flush();

        boolean courseExists = testService.exists(1);
        boolean noTag = testService.hasSection(1, 1);
        assertThat(courseExists).isTrue();
        assertThat(noTag).isFalse();

        course.addTag(tag);
        tag.addCourse(course);
        testEntityRepository.saveTag(tag);
        testEntityRepository.saveCourse(course);
        entityManager.flush();
        entityManager.clear();

        boolean hasTag1 = testService.hasTag(1, 1);
        boolean hasTag2 = testService.hasTag(1, 2);

        assertThat(hasTag1).isTrue();
        assertThat(hasTag2).isFalse();
    }
}
