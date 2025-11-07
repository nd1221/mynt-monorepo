package com.example.mynt_finance_backend.learningPlatformTests.services.impl;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.SectionDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.CourseEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.LessonEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.SectionEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TestEntity;
import com.example.mynt_finance_backend.learningPlatform.services.SectionService;
import com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries.TestContainerConfiguration;
import com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries.TestEntityRepositoryFacade;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SectionServiceIntegrationTests implements TestContainerConfiguration {

    @Autowired
    private SectionService testService;

    @Autowired
    private TestEntityRepositoryFacade testEntityRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testExists() {

        SectionEntity section = SectionTestUtil.createEmptySection();
        testEntityRepository.saveSection(section);
        entityManager.flush();

        boolean exists = testService.exists(1);
        assertThat(exists).isTrue();
    }

//    @Test
//    public void testCreateSection() {
//
//        SectionDTO dto = SectionTestUtil.createFullSectionDTO();
//        SectionDTO createdDTO = testService.createSection(dto);
//        entityManager.flush();
//
//        SectionEntity createdEntity = testEntityRepository.findSectionById(1);
//
//        assertThat(createdDTO).isNotNull();
//        assertThat(createdEntity).isNotNull();
//        assertThat(createdDTO.id()).isEqualTo(1);
//        assertThat(createdDTO.id()).isEqualTo(createdEntity.getId());
//        assertThat(createdDTO.title()).isEqualTo(dto.title());
//        assertThat(createdDTO.title()).isEqualTo(createdEntity.getTitle());
//        assertThat(createdDTO.description()).isEqualTo(dto.description());
//        assertThat(createdDTO.description()).isEqualTo(createdEntity.getDescription());
//        assertThat(createdDTO.position()).isEqualTo(dto.position());
//        assertThat(createdDTO.position()).isEqualTo(createdEntity.getPosition());
//        assertThat(createdDTO.courseId()).isNotEqualTo(dto.courseId());
//        assertThat(createdDTO.courseId()).isNotPresent();
//        assertThat(createdDTO.lessonIds()).isNotEqualTo(dto.lessonIds());
//        assertThat(createdDTO.lessonIds()).isEmpty();
//        assertThat(createdDTO.testId()).isNotEqualTo(dto.testId());
//        assertThat(createdDTO.testId()).isNotPresent();
//        assertThat(createdEntity.getCourse()).isNull();
//        assertThat(createdEntity.getLessons()).isNull();
//        assertThat(createdEntity.getTest()).isNull();
//    }

    @Test
    public void testReadSectionById() {

        SectionEntity section = SectionTestUtil.createConvertedSection();
        testEntityRepository.saveCourse(section.getCourse());
        testEntityRepository.saveLessons(section.getLessons());
        testEntityRepository.saveTest(section.getTest());
        testEntityRepository.saveSection(section);
        entityManager.flush();

        SectionDTO foundDTO = testService.readSectionById(1);

        assertThat(foundDTO).isNotNull();
        assertThat(foundDTO.id()).isEqualTo(1);
        assertThat(foundDTO.title()).isEqualTo(section.getTitle());
        assertThat(foundDTO.description()).isEqualTo(section.getDescription());
        assertThat(foundDTO.position()).isEqualTo(section.getPosition());
        assertThat(foundDTO.courseId()).isPresent();
        assertThat(foundDTO.courseId().get()).isEqualTo(section.getCourse().getId());
        assertThat(foundDTO.lessonIds()).isEqualTo(section.getLessons().stream().map(LessonEntity::getId).collect(Collectors.toSet()));
        assertThat(foundDTO.testId()).isPresent();
        assertThat(foundDTO.testId().get()).isEqualTo(section.getTest().getId());
    }

    @Test
    public void testReadAllSections() {

        List<SectionDTO> foundSections = testService.readAllSections();

        assertThat(foundSections).isNotNull();
        assertThat(foundSections).isEmpty();

        SectionEntity section1 = SectionTestUtil.createConvertedSection();
        testEntityRepository.saveCourse(section1.getCourse());
        testEntityRepository.saveLessons(section1.getLessons());
        testEntityRepository.saveTest(section1.getTest());
        testEntityRepository.saveSection(section1);
        entityManager.flush();

        SectionEntity section2 = SectionTestUtil.createConvertedSection();
        testEntityRepository.saveCourse(section2.getCourse());
        testEntityRepository.saveLessons(section2.getLessons());
        testEntityRepository.saveTest(section2.getTest());
        testEntityRepository.saveSection(section2);
        entityManager.flush();

        foundSections = testService.readAllSections();

        assertThat(foundSections).isNotNull();
        assertThat(foundSections).hasSize(2);
        assertThat(foundSections.get(0)).isNotNull();
        assertThat(foundSections.get(1)).isNotNull();
        assertThat(foundSections.get(0)).isNotEqualTo(foundSections.get(1));

        assertThat(foundSections.get(0).id()).isEqualTo(1);
        assertThat(foundSections.get(0).title()).isEqualTo(section1.getTitle());
        assertThat(foundSections.get(0).description()).isEqualTo(section1.getDescription());
        assertThat(foundSections.get(0).position()).isEqualTo(section1.getPosition());
        assertThat(foundSections.get(0).courseId()).isPresent();
        assertThat(foundSections.get(0).courseId().get()).isEqualTo(section1.getCourse().getId());
        assertThat(foundSections.get(0).lessonIds()).isEqualTo(section1.getLessons().stream().map(LessonEntity::getId).collect(Collectors.toSet()));
        assertThat(foundSections.get(0).testId()).isPresent();
        assertThat(foundSections.get(0).testId().get()).isEqualTo(section1.getTest().getId());
    }

    @Test
    public void testFindAllSectionsById() {

        Set<SectionEntity> foundSections = testService.findAllSectionsById(Set.of(1, 2, 3));

        assertThat(foundSections).isNotNull();
        assertThat(foundSections).isEmpty();

        Set<SectionEntity> sections = SectionTestUtil.createSectionSet();
        testEntityRepository.saveSections(sections);
        entityManager.flush();

        foundSections = testService.findAllSectionsById(Set.of(1, 2));

        assertThat(foundSections).isNotNull();
        assertThat(foundSections).isNotEmpty();
        assertThat(foundSections).hasSize(2);
        foundSections.forEach(section -> assertThat(section.getId()).isNotEqualTo(3));
    }

    @Test
    public void testFindSectionById() {

        SectionEntity section = SectionTestUtil.createConvertedSection();
        testEntityRepository.saveCourse(section.getCourse());
        testEntityRepository.saveLessons(section.getLessons());
        testEntityRepository.saveTest(section.getTest());
        testEntityRepository.saveSection(section);
        entityManager.flush();

        SectionEntity foundEntity = testService.findSectionById(1);

        assertThat(foundEntity).isNotNull();
        assertThat(foundEntity.getId()).isEqualTo(1);
        assertThat(foundEntity.getTitle()).isEqualTo(section.getTitle());
        assertThat(foundEntity.getDescription()).isEqualTo(section.getDescription());
        assertThat(foundEntity.getPosition()).isEqualTo(section.getPosition());
        assertThat(foundEntity.getCourse()).isEqualTo(section.getCourse());
        assertThat(foundEntity.getLessons()).isEqualTo(section.getLessons());
        assertThat(foundEntity.getTest()).isEqualTo(section.getTest());
    }

    @Test
    public void testUpdateSection() {

        SectionEntity section = SectionTestUtil.createConvertedSection();
        testEntityRepository.saveCourse(section.getCourse());
        testEntityRepository.saveLessons(section.getLessons());
        testEntityRepository.saveTest(section.getTest());
        testEntityRepository.saveSection(section);
        entityManager.flush();
        entityManager.clear();

        SectionDTO dto = new SectionDTO(
                2,
                "newTitle",
                "newDescription",
                2,
                Optional.of(10),
                CommonTestUtils.getIntegerIds(),
                Optional.of(3),
                1000
        );

        SectionDTO updatedDTO = testService.updateSection(dto, 1);
        SectionEntity updatedEntity = testEntityRepository.findSectionById(1);

        assertThat(updatedDTO).isNotNull();
        assertThat(updatedEntity).isNotNull();
        assertThat(updatedDTO.id()).isEqualTo(1);
        assertThat(updatedDTO.id()).isEqualTo(updatedEntity.getId());
        assertThat(updatedDTO.title()).isEqualTo(dto.title());
        assertThat(updatedDTO.title()).isEqualTo(updatedEntity.getTitle());
        assertThat(updatedDTO.description()).isEqualTo(dto.description());
        assertThat(updatedDTO.description()).isEqualTo(updatedEntity.getDescription());
        assertThat(updatedDTO.position()).isEqualTo(dto.position());
        assertThat(updatedDTO.position()).isEqualTo(updatedEntity.getPosition());
        assertThat(updatedDTO.courseId()).isNotEqualTo(dto.courseId());
        assertThat(updatedDTO.courseId()).isPresent();
        assertThat(updatedDTO.courseId().get()).isEqualTo(updatedEntity.getCourse().getId());
        assertThat(updatedDTO.lessonIds()).isNotEqualTo(dto.lessonIds());
        assertThat(updatedDTO.lessonIds()).isEqualTo(updatedEntity.getLessons().stream().map(LessonEntity::getId).collect(Collectors.toSet()));
        assertThat(updatedDTO.testId()).isNotEqualTo(dto.testId());
        assertThat(updatedDTO.testId()).isPresent();
        assertThat(updatedDTO.testId().get()).isEqualTo(updatedEntity.getTest().getId());
    }

    @Test
    public void testDeleteSection() {

        CourseEntity course = CourseTestUtil.createEmptyCourse();
        testEntityRepository.saveCourse(course);
        entityManager.flush();

        Set<LessonEntity> lessons = LessonTestUtil.createLessonSet();
        testEntityRepository.saveLessons(lessons);
        entityManager.flush();

        TestEntity test = TestTestEntityUtil.createEmptyTest();
        testEntityRepository.saveTest(test);
        entityManager.flush();

        SectionEntity section = SectionTestUtil.createEmptySection();
        testEntityRepository.saveSection(section);
        entityManager.flush();

        section.setCourse(course);
        course.addSection(section);
        section.setLessons(lessons);
        lessons.forEach(l-> l.setSection(section));
        section.setTest(test);
        test.setSection(section);
        testEntityRepository.saveCourse(course);
        testEntityRepository.saveLessons(lessons);
        testEntityRepository.saveTest(test);
        testEntityRepository.saveSection(section);
        entityManager.flush();

        testService.deleteSection(1);
        entityManager.flush();
        entityManager.clear();

        SectionEntity foundEntity = testEntityRepository.findSectionById(1);
        CourseEntity foundCourse = testEntityRepository.findCourseById(1);
        Set<LessonEntity> foundLessons = testEntityRepository.findAllLessons();
        TestEntity foundTest = testEntityRepository.findTestById(1);

        assertThat(foundEntity).isNull();
        assertThat(foundCourse).isNotNull();
        assertThat(foundCourse.getSections().stream().map(SectionEntity::getId).toList()).doesNotContain(1);
        assertThat(foundLessons).isEmpty();
        assertThat(foundTest).isNull();
    }

    @Test
    public void testAddLesson() {

        LessonEntity lesson = LessonTestUtil.createEmptyLesson();
        testEntityRepository.saveLesson(lesson);
        entityManager.flush();

        SectionEntity section = SectionTestUtil.createEmptySection();
        testEntityRepository.saveSection(section);
        entityManager.flush();
        entityManager.clear();

        SectionDTO updatedDTO = testService.addLesson(1, 1);
        entityManager.flush();

        SectionEntity updatedEntity = testEntityRepository.findSectionById(1);
        LessonEntity updatedLesson = testEntityRepository.findLessonById(1);

        assertThat(updatedDTO.id()).isEqualTo(updatedEntity.getId());
        assertThat(updatedDTO.lessonIds()).contains(updatedLesson.getId());
        assertThat(updatedEntity.getLessons()).contains(updatedLesson);
        assertThat(updatedLesson.getSection()).isEqualTo(updatedEntity);
    }

    @Test
    public void testRemoveLesson() {

        LessonEntity lesson = LessonTestUtil.createEmptyLesson();
        testEntityRepository.saveLesson(lesson);
        entityManager.flush();

        SectionEntity section = SectionTestUtil.createEmptySection();
        testEntityRepository.saveSection(section);
        entityManager.flush();

        section.addLesson(lesson);
        lesson.setSection(section);
        testEntityRepository.saveLesson(lesson);
        testEntityRepository.saveSection(section);
        entityManager.flush();

        SectionDTO updatedDTO = testService.removeLesson(1, 1);
        entityManager.flush();

        SectionEntity updatedEntity = testEntityRepository.findSectionById(1);
        LessonEntity updatedLesson = testEntityRepository.findLessonById(1);

        assertThat(updatedDTO.id()).isEqualTo(updatedEntity.getId());
        assertThat(updatedLesson).isNull();
        assertThat(updatedDTO.lessonIds()).doesNotContain(lesson.getId());
        assertThat(updatedEntity.getLessons()).doesNotContain(lesson);
    }

    @Test
    public void testHasTest() {

        TestEntity test = TestTestEntityUtil.createEmptyTest();
        testEntityRepository.saveTest(test);
        entityManager.flush();

        SectionEntity section = SectionTestUtil.createEmptySection();
        testEntityRepository.saveSection(section);
        entityManager.flush();

        boolean hasTest = testService.hasTest(1);
        assertThat(hasTest).isFalse();

        section.setTest(test);
        test.setSection(section);
        testEntityRepository.saveTest(test);
        testEntityRepository.saveSection(section);
        entityManager.flush();

        hasTest = testService.hasTest(1);
        assertThat(hasTest).isTrue();
    }

    @Test
    public void testGetTestId() {

        TestEntity test = TestTestEntityUtil.createEmptyTest();
        testEntityRepository.saveTest(test);
        entityManager.flush();

        SectionEntity section = SectionTestUtil.createEmptySection();
        testEntityRepository.saveSection(section);
        entityManager.flush();

        Integer testId = testService.getTestId(1);
        assertThat(testId).isNull();

        section.setTest(test);
        test.setSection(section);
        testEntityRepository.saveTest(test);
        testEntityRepository.saveSection(section);
        entityManager.flush();

        testId = testService.getTestId(1);
        assertThat(testId).isNotNull();
        assertThat(testId).isEqualTo(1);
    }

    @Test
    public void testHasLesson() {

        LessonEntity lesson = LessonTestUtil.createEmptyLesson();
        testEntityRepository.saveLesson(lesson);
        entityManager.flush();

        SectionEntity section = SectionTestUtil.createEmptySection();
        testEntityRepository.saveSection(section);
        entityManager.flush();

        boolean sectionExists = testService.exists(1);
        boolean noLesson = testService.hasLesson(1, 1);
        assertThat(sectionExists).isTrue();
        assertThat(noLesson).isFalse();

        section.addLesson(lesson);
        lesson.setSection(section);
        testEntityRepository.saveLesson(lesson);
        testEntityRepository.saveSection(section);
        entityManager.flush();

        boolean hasLesson1 = testService.hasLesson(1, 1);
        boolean hasLesson2 = testService.hasLesson(1, 2);

        assertThat(hasLesson1).isTrue();
        assertThat(hasLesson2).isFalse();
    }
}
