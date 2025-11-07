package com.example.mynt_finance_backend.learningPlatformTests.services.impl;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.LessonDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.*;
import com.example.mynt_finance_backend.learningPlatform.services.LessonService;
import com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries.TestContainerConfiguration;
import com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries.TestEntityRepositoryFacade;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.CommonTestUtils;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.LessonTestUtil;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.QuestionTestUtil;
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
import java.util.Set;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LessonServiceIntegrationTests implements TestContainerConfiguration {

    @Autowired
    private LessonService testService;

    @Autowired
    private TestEntityRepositoryFacade testEntityRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testExists() {

        LessonEntity lesson = LessonTestUtil.createEmptyLesson();
        testEntityRepository.saveLesson(lesson);
        entityManager.flush();

        boolean exists = testService.exists(1);
        assertThat(exists).isTrue();
    }

//    @Test
//    public void testCreateLesson() {
//
//        LessonDTO dto = LessonTestUtil.createFullLessonDTO();
//        LessonDTO createdDTO = testService.createLesson(dto);
//        entityManager.flush();
//
//        LessonEntity createdEntity = testEntityRepository.findLessonById(1);
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
//        assertThat(createdDTO.sectionId()).isNotPresent();
//        assertThat(createdEntity.getSection()).isNull();
//        assertThat(createdDTO.questionIds()).isEmpty();
//        assertThat(createdEntity.getQuestions()).isNull();
//    }

//    @Test
//    public void testReadLessonById() {
//
//        LessonEntity lesson = LessonTestUtil.createConvertedLesson();
//        testEntityRepository.saveSection(lesson.getSection());
//        testEntityRepository.saveQuestions(lesson.getQuestions());
//        testEntityRepository.saveLesson(lesson);
//        entityManager.flush();
//
//        LessonDTO foundDTO = testService.readLessonById(1);
//
//        assertThat(foundDTO).isNotNull();
//        assertThat(foundDTO.id()).isEqualTo(lesson.getId());
//        assertThat(foundDTO.title()).isEqualTo(lesson.getTitle());
//        assertThat(foundDTO.description()).isEqualTo(lesson.getDescription());
//        assertThat(foundDTO.position()).isEqualTo(lesson.getPosition());
//        assertThat(foundDTO.sectionId()).isPresent();
//        assertThat(foundDTO.sectionId().get()).isEqualTo(lesson.getSection().getId());
//        assertThat(foundDTO.questionIds()).isEqualTo(lesson.getQuestions().stream().map(QuestionEntity::getId).collect(Collectors.toSet()));
//    }

//    @Test
//    public void testReadAllLessons() {
//
//        List<LessonDTO> foundLessons = testService.readAllLessons();
//
//        assertThat(foundLessons).isNotNull();
//        assertThat(foundLessons).isEmpty();
//
//        LessonEntity lesson1 = LessonTestUtil.createConvertedLesson();
//        testEntityRepository.saveSection(lesson1.getSection());
//        testEntityRepository.saveQuestions(lesson1.getQuestions());
//        testEntityRepository.saveLesson(lesson1);
//        entityManager.flush();
//
//        LessonEntity lesson2 = LessonTestUtil.createConvertedLesson();
//        testEntityRepository.saveSection(lesson2.getSection());
//        testEntityRepository.saveQuestions(lesson2.getQuestions());
//        testEntityRepository.saveLesson(lesson2);
//        entityManager.flush();
//
//        foundLessons = testService.readAllLessons();
//
//        assertThat(foundLessons).isNotNull();
//        assertThat(foundLessons).isNotEmpty();
//        assertThat(foundLessons).hasSize(2);
//        assertThat(foundLessons.get(0)).isNotNull();
//        assertThat(foundLessons.get(1)).isNotNull();
//        assertThat(foundLessons.get(0)).isNotEqualTo(foundLessons.get(1));
//
//        assertThat(foundLessons.get(0).id()).isEqualTo(lesson1.getId());
//        assertThat(foundLessons.get(0).title()).isEqualTo(lesson1.getTitle());
//        assertThat(foundLessons.get(0).description()).isEqualTo(lesson1.getDescription());
//        assertThat(foundLessons.get(0).position()).isEqualTo(lesson1.getPosition());
//        assertThat(foundLessons.get(0).sectionId()).isPresent();
//        assertThat(foundLessons.get(0).sectionId().get()).isEqualTo(lesson1.getSection().getId());
//        assertThat(foundLessons.get(0).questionIds()).isEqualTo(lesson1.getQuestions().stream().map(QuestionEntity::getId).collect(Collectors.toSet()));
//    }

    @Test
    public void testFindAllLessonsById() {

        Set<LessonEntity> foundLessons = testService.findAllLessonsById(Set.of(1, 3));

        assertThat(foundLessons).isNotNull();
        assertThat(foundLessons).isEmpty();

        Set<LessonEntity> lessons = LessonTestUtil.createLessonSet();
        testEntityRepository.saveLessons(lessons);
        entityManager.flush();

        foundLessons = testService.findAllLessonsById(Set.of(1, 3));

        assertThat(foundLessons).isNotNull();
        assertThat(foundLessons).isNotEmpty();
        assertThat(foundLessons).hasSize(2);
        foundLessons.forEach(lesson -> assertThat(lesson.getId()).isNotEqualTo(2));
    }

    @Test
    public void testFindLessonById() {

        LessonEntity lesson = LessonTestUtil.createConvertedLesson();
        testEntityRepository.saveSection(lesson.getSection());
        testEntityRepository.saveQuestions(lesson.getQuestions());
        testEntityRepository.saveLesson(lesson);
        entityManager.flush();

        LessonEntity foundEntity = testService.findLessonById(1);

        assertThat(foundEntity.getId()).isEqualTo(lesson.getId());
        assertThat(foundEntity.getTitle()).isEqualTo(lesson.getTitle());
        assertThat(foundEntity.getDescription()).isEqualTo(lesson.getDescription());
        assertThat(foundEntity.getPosition()).isEqualTo(lesson.getPosition());
        assertThat(foundEntity.getContent()).isEqualTo(lesson.getContent());
        assertThat(foundEntity.getSection()).isEqualTo(lesson.getSection());
        assertThat(foundEntity.getQuestions()).isEqualTo(lesson.getQuestions());
    }

//    @Test
//    public void testUpdateLesson() {
//
//        LessonEntity lesson = LessonTestUtil.createConvertedLesson();
//        testEntityRepository.saveSection(lesson.getSection());
//        testEntityRepository.saveQuestions(lesson.getQuestions());
//        testEntityRepository.saveLesson(lesson);
//        entityManager.flush();
//
//        LessonDTO dto = new LessonDTO(
//                3,
//                "newTitle",
//                "newDescription",
//                5,
//                Optional.of(10),
//                1,
//                CommonTestUtils.getUpdatedLongIds(),
//                1000,
//                7,
//                12,
//                5
//        );
//
//        LessonDTO updatedDTO = testService.updateLesson(dto, 1);
//        entityManager.flush();
//
//        LessonEntity updatedEntity = testEntityRepository.findLessonById(1);
//
//        assertThat(updatedDTO).isNotNull();
//        assertThat(updatedEntity).isNotNull();
//        assertThat(updatedDTO.id()).isEqualTo(updatedEntity.getId());
//        assertThat(dto.id()).isNotEqualTo(updatedEntity.getId());
//        assertThat(updatedDTO.title()).isEqualTo(updatedEntity.getTitle());
//        assertThat(dto.title()).isEqualTo(updatedEntity.getTitle());
//        assertThat(updatedDTO.description()).isEqualTo(updatedEntity.getDescription());
//        assertThat(dto.description()).isEqualTo(updatedEntity.getDescription());
//        assertThat(updatedDTO.position()).isEqualTo(updatedEntity.getPosition());
//        assertThat(dto.position()).isEqualTo(updatedEntity.getPosition());
//        assertThat(updatedDTO.sectionId()).isPresent();
//        assertThat(updatedDTO.sectionId().get()).isEqualTo(updatedEntity.getSection().getId());
//        assertThat(updatedDTO.sectionId()).isNotEqualTo(dto.sectionId());
//        assertThat(updatedDTO.questionIds()).isEqualTo(updatedEntity.getQuestions().stream().map(QuestionEntity::getId).collect(Collectors.toSet()));
//        assertThat(updatedDTO.questionIds()).isNotEqualTo(dto.questionIds());
//    }

    @Test
    public void testDeleteLesson() {

        LessonEntity lesson = LessonTestUtil.createConvertedLesson();
        testEntityRepository.saveSection(lesson.getSection());
        testEntityRepository.saveQuestions(lesson.getQuestions());
        testEntityRepository.saveLesson(lesson);
        entityManager.flush();

        testService.deleteLesson(1);
        entityManager.flush();
        entityManager.clear();

        LessonEntity foundLesson = testEntityRepository.findLessonById(1);
        SectionEntity foundSection = testEntityRepository.findSectionById(1);
        Set<QuestionEntity> foundQuestions = testEntityRepository.findAllQuestions();

        assertThat(foundLesson).isNull();
        assertThat(foundSection).isNotNull();
        assertThat(foundSection.getLessons().stream().map(LessonEntity::getId).toList()).doesNotContain(1);
        assertThat(foundQuestions).isNotNull();
        assertThat(foundQuestions).isEmpty();
    }

//    @Test
//    public void testAddQuestion() {
//
//        MultipleChoiceQuestionEntity question1 = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
//        TrueFalseQuestionEntity question2 = QuestionTestUtil.createEmptyTrueFalseQuestion();
//        testEntityRepository.saveQuestion(question1);
//        testEntityRepository.saveQuestion(question2);
//        entityManager.flush();
//
//        LessonEntity lesson = LessonTestUtil.createEmptyLesson();
//        testEntityRepository.saveLesson(lesson);
//        entityManager.flush();
//        entityManager.clear();
//
//        LessonDTO updatedDTO = testService.addQuestion(1 , 1L);
//        LessonEntity updatedEntity = testEntityRepository.findLessonById(1);
//        MultipleChoiceQuestionEntity updatedQuestion1 = (MultipleChoiceQuestionEntity) testEntityRepository.findQuestionById(1L);
//        entityManager.flush();
//
//        assertThat(updatedDTO.id()).isEqualTo(updatedEntity.getId());
//        assertThat(updatedDTO.questionIds()).contains(updatedQuestion1.getId());
//        assertThat(updatedEntity.getQuestions()).contains(updatedQuestion1);
//        assertThat(updatedQuestion1.getLesson()).isEqualTo(updatedEntity);
//
//        updatedDTO = testService.addQuestion(1, 2L);
//        TrueFalseQuestionEntity updatedQuestion2 = (TrueFalseQuestionEntity) testEntityRepository.findQuestionById(2L);
//        entityManager.flush();
//
//        assertThat(updatedDTO.id()).isEqualTo(updatedEntity.getId());
//        assertThat(updatedDTO.questionIds()).contains(updatedQuestion1.getId());
//        assertThat(updatedEntity.getQuestions()).contains(updatedQuestion1);
//        assertThat(updatedQuestion1.getLesson()).isEqualTo(updatedEntity);
//        assertThat(updatedDTO.questionIds()).contains(updatedQuestion2.getId());
//        assertThat(updatedEntity.getQuestions()).contains(updatedQuestion2);
//        assertThat(updatedQuestion2.getLesson()).isEqualTo(updatedEntity);
//    }

//    @Test
//    public void testRemoveQuestion() {
//
//        MultipleChoiceQuestionEntity question1 = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
//        TrueFalseQuestionEntity question2 = QuestionTestUtil.createEmptyTrueFalseQuestion();
//        testEntityRepository.saveQuestion(question1);
//        testEntityRepository.saveQuestion(question2);
//        entityManager.flush();
//
//        LessonEntity lesson = LessonTestUtil.createEmptyLesson();
//        testEntityRepository.saveLesson(lesson);
//        entityManager.flush();
//
//        lesson.addQuestion(question1);
//        lesson.addQuestion(question2);
//        question1.setLesson(lesson);
//        question2.setLesson(lesson);
//        testEntityRepository.saveQuestion(question1);
//        testEntityRepository.saveQuestion(question2);
//        testEntityRepository.saveLesson(lesson);
//        entityManager.flush();
//
//        LessonDTO updatedDTO = testService.removeQuestion(1 , 1L);
//        LessonEntity updatedEntity = testEntityRepository.findLessonById(1);
//        MultipleChoiceQuestionEntity updatedQuestion1 = (MultipleChoiceQuestionEntity) testEntityRepository.findQuestionById(1L);
//        TrueFalseQuestionEntity updatedQuestion2 = (TrueFalseQuestionEntity) testEntityRepository.findQuestionById(2L);
//        entityManager.flush();
//
//        assertThat(updatedDTO.id()).isEqualTo(updatedEntity.getId());
//        assertThat(updatedQuestion1).isNull();
//        assertThat(updatedDTO.questionIds()).doesNotContain(question1.getId());
//        assertThat(updatedEntity.getQuestions()).doesNotContain(question1);
//        assertThat(updatedQuestion2).isNotNull();
//        assertThat(updatedDTO.questionIds()).contains(updatedQuestion2.getId());
//        assertThat(updatedEntity.getQuestions()).contains(updatedQuestion2);
//        assertThat(updatedQuestion2.getLesson()).isEqualTo(updatedEntity);
//
//        updatedDTO = testService.removeQuestion(1, 2L);
//        entityManager.flush();
//
//        updatedQuestion1 = (MultipleChoiceQuestionEntity) testEntityRepository.findQuestionById(1L);
//        updatedQuestion2 = (TrueFalseQuestionEntity) testEntityRepository.findQuestionById(2L);
//
//        assertThat(updatedDTO.id()).isEqualTo(updatedEntity.getId());
//        assertThat(updatedQuestion1).isNull();
//        assertThat(updatedQuestion2).isNull();
//        assertThat(updatedDTO.questionIds()).doesNotContain(question1.getId());
//        assertThat(updatedEntity.getQuestions()).doesNotContain(question1);
//        assertThat(updatedDTO.questionIds()).doesNotContain(question2.getId());
//        assertThat(updatedEntity.getQuestions()).doesNotContain(question2);
//    }

    @Test
    public void testHasQuestion() {

        MultipleChoiceQuestionEntity question1 = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
        TrueFalseQuestionEntity question2 = QuestionTestUtil.createEmptyTrueFalseQuestion();
        testEntityRepository.saveQuestion(question1);
        testEntityRepository.saveQuestion(question2);
        entityManager.flush();

        LessonEntity lesson = LessonTestUtil.createEmptyLesson();
        testEntityRepository.saveLesson(lesson);
        entityManager.flush();

        boolean lessonExists = testService.exists(1);
        boolean noQuestion1 = testService.hasQuestion(1, 1L);
        boolean noQuestion2 = testService.hasQuestion(1, 2L);
        assertThat(lessonExists).isTrue();
        assertThat(noQuestion1).isFalse();
        assertThat(noQuestion2).isFalse();

        lesson.addQuestion(question1);
        lesson.addQuestion(question2);
        question1.setLesson(lesson);
        question2.setLesson(lesson);
        testEntityRepository.saveQuestion(question1);
        testEntityRepository.saveQuestion(question2);
        testEntityRepository.saveLesson(lesson);
        entityManager.flush();
        entityManager.clear();

        boolean hasQuestion1 = testService.hasQuestion(1, 1L);
        boolean hasQuestion2 = testService.hasQuestion(1, 2L);
        boolean hasQuestion3 = testService.hasQuestion(1, 3L);

        assertThat(hasQuestion1).isTrue();
        assertThat(hasQuestion2).isTrue();
        assertThat(hasQuestion3).isFalse();
    }
}
