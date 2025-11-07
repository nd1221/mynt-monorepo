package com.example.mynt_finance_backend.learningPlatformTests.services.impl;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.MultipleChoiceQuestionDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.QuestionDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.TrueFalseQuestionDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.*;
import com.example.mynt_finance_backend.learningPlatform.domain.entityEnums.QuestionType;
import com.example.mynt_finance_backend.learningPlatform.services.QuestionService;
import com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries.TestContainerConfiguration;
import com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries.TestEntityRepositoryFacade;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.ChoiceTestUtil;
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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class QuestionServiceIntegrationTests implements TestContainerConfiguration {

    @Autowired
    private QuestionService testService;

    @Autowired
    private TestEntityRepositoryFacade testEntityRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testExists() {

        boolean noneExists = testService.exists(1L);
        assertThat(noneExists).isFalse();

        MultipleChoiceQuestionEntity question1 = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
        TrueFalseQuestionEntity question2 = QuestionTestUtil.createEmptyTrueFalseQuestion();
        testEntityRepository.saveQuestion(question1);
        testEntityRepository.saveQuestion(question2);
        entityManager.flush();

        boolean multipleChoiceExists = testService.exists(1L);
        boolean trueFalseExists = testService.exists(2L);

        assertThat(multipleChoiceExists).isTrue();
        assertThat(trueFalseExists).isTrue();
    }

    @Test
    public void testMultipleChoiceQuestionExists() {

        boolean noneExists = testService.exists(1L);
        assertThat(noneExists).isFalse();

        MultipleChoiceQuestionEntity question1 = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
        TrueFalseQuestionEntity question2 = QuestionTestUtil.createEmptyTrueFalseQuestion();
        testEntityRepository.saveQuestion(question1);
        testEntityRepository.saveQuestion(question2);
        entityManager.flush();

        boolean multipleChoiceExists = testService.multipleChoiceQuestionExists(1L);
        boolean trueFalseExists = testService.multipleChoiceQuestionExists(2L);

        assertThat(multipleChoiceExists).isTrue();
        assertThat(trueFalseExists).isFalse();
    }

    @Test
    public void testTrueFalseQuestionExists() {

        boolean noneExists = testService.exists(1L);
        assertThat(noneExists).isFalse();

        MultipleChoiceQuestionEntity question1 = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
        TrueFalseQuestionEntity question2 = QuestionTestUtil.createEmptyTrueFalseQuestion();
        testEntityRepository.saveQuestion(question1);
        testEntityRepository.saveQuestion(question2);
        entityManager.flush();

        boolean multipleChoiceExists = testService.trueFalseQuestionExists(1L);
        boolean trueFalseExists = testService.trueFalseQuestionExists(2L);

        assertThat(multipleChoiceExists).isFalse();
        assertThat(trueFalseExists).isTrue();
    }

//    @Test
//    public void testCreateTrueFalseQuestion() {
//
//        TrueFalseQuestionDTO dto = QuestionTestUtil.createFullTrueFalseQuestionDTO();
//        TrueFalseQuestionDTO createdDTO = testService.createQuestion(dto);
//        entityManager.flush();
//
//        TrueFalseQuestionEntity createdEntity = (TrueFalseQuestionEntity) testEntityRepository.findQuestionById(1L);
//
//        assertThat(createdDTO).isNotNull();
//        assertThat(createdEntity).isNotNull();
//        assertThat(createdDTO.getId()).isEqualTo(1);
//        assertThat(createdDTO.getId()).isNotEqualTo(dto.getId());
//        assertThat(createdEntity.getId()).isEqualTo(createdDTO.getId());
//        assertThat(createdDTO.getQuestionText()).isEqualTo(dto.getQuestionText());
//        assertThat(createdEntity.getQuestionText()).isEqualTo(dto.getQuestionText());
//        assertThat(createdDTO.getQuestionType()).isEqualTo(dto.getQuestionType());
//        assertThat(createdEntity.getQuestionType()).isEqualTo(dto.getQuestionType());
//        assertThat(createdDTO.getLessonId()).isNotPresent();
//        assertThat(createdDTO.getLessonId()).isNotEqualTo(dto.getLessonId());
//        assertThat(createdEntity.getLesson()).isNull();
//        assertThat(createdDTO.getCorrect()).isEqualTo(dto.getCorrect());
//        assertThat(createdEntity.getCorrect()).isEqualTo(dto.getCorrect());
//    }

//    @Test
//    public void testCreateMultipleChoiceQuestion() {
//
//        MultipleChoiceQuestionDTO dto = QuestionTestUtil.createFullMultipleChoiceQuestionDTO();
//        MultipleChoiceQuestionDTO createdDTO = testService.createQuestion(dto);
//        entityManager.flush();
//
//        MultipleChoiceQuestionEntity createdEntity = (MultipleChoiceQuestionEntity) testEntityRepository.findQuestionById(1L);
//
//        assertThat(createdDTO).isNotNull();
//        assertThat(createdEntity).isNotNull();
//        assertThat(createdDTO.getId()).isEqualTo(1);
//        assertThat(createdDTO.getId()).isNotEqualTo(dto.getId());
//        assertThat(createdEntity.getId()).isEqualTo(createdDTO.getId());
//        assertThat(createdDTO.getQuestionText()).isEqualTo(dto.getQuestionText());
//        assertThat(createdEntity.getQuestionText()).isEqualTo(dto.getQuestionText());
//        assertThat(createdDTO.getQuestionType()).isEqualTo(dto.getQuestionType());
//        assertThat(createdEntity.getQuestionType()).isEqualTo(dto.getQuestionType());
//        assertThat(createdDTO.getLessonId()).isNotPresent();
//        assertThat(createdDTO.getLessonId()).isNotEqualTo(dto.getLessonId());
//        assertThat(createdEntity.getLesson()).isNull();
//        assertThat(createdEntity.getChoices()).isNull();
//    }

//    @Test
//    public void testReadQuestionById() {
//
////        QuestionDTO foundNone = testService.readQuestionById(1);
////        assertThat(foundNone).isNull();
//
//        LessonEntity lesson = LessonTestUtil.createEmptyLesson();
//        testEntityRepository.saveLesson(lesson);
//        entityManager.flush();
//
//        Set<ChoiceEntity> choices = ChoiceTestUtil.createEmptyChoiceSet();
//        testEntityRepository.saveChoices(choices);
//        entityManager.flush();
//
//        MultipleChoiceQuestionEntity question1 = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
//        TrueFalseQuestionEntity question2 = QuestionTestUtil.createEmptyTrueFalseQuestion();
//        testEntityRepository.saveQuestion(question1);
//        testEntityRepository.saveQuestion(question2);
//        entityManager.flush();
//
//        question1.setLesson(lesson);
//        lesson.addQuestion(question1);
//        question1.setChoices(choices);
//        choices.forEach(c -> c.setQuestion(question1));
//        question2.setLesson(lesson);
//        lesson.addQuestion(question2);
//
//        testEntityRepository.saveLesson(lesson);
//        testEntityRepository.saveChoices(choices);
//        testEntityRepository.saveQuestion(question1);
//        testEntityRepository.saveQuestion(question2);
//        entityManager.flush();
//
//        QuestionDTO foundQuestion1 = testService.readQuestionById(1L);
//        QuestionDTO foundQuestion2 = testService.readQuestionById(2L);
//
//        assertThat(foundQuestion1).isNotNull();
//        assertThat(foundQuestion2).isNotNull();
//        assertThat(foundQuestion1.getId()).isEqualTo(1);
//        assertThat(foundQuestion2.getId()).isEqualTo(2);
//        assertThat(foundQuestion1).isNotEqualTo(foundQuestion2);
//        assertThat(foundQuestion1).isInstanceOf(MultipleChoiceQuestionDTO.class);
//        assertThat(foundQuestion2).isInstanceOf(TrueFalseQuestionDTO.class);
//    }

//    @Test
//    public void testReadAllQuestions() {
//
//        List<QuestionDTO> foundNone = testService.readAllQuestions();
//        assertThat(foundNone).isNotNull();
//        assertThat(foundNone).isEmpty();
//
//        LessonEntity lesson = LessonTestUtil.createEmptyLesson();
//        testEntityRepository.saveLesson(lesson);
//        entityManager.flush();
//
//        Set<ChoiceEntity> choices = ChoiceTestUtil.createEmptyChoiceSet();
//        testEntityRepository.saveChoices(choices);
//        entityManager.flush();
//
//        MultipleChoiceQuestionEntity question1 = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
//        TrueFalseQuestionEntity question2 = QuestionTestUtil.createEmptyTrueFalseQuestion();
//        testEntityRepository.saveQuestion(question1);
//        testEntityRepository.saveQuestion(question2);
//        entityManager.flush();
//
//        question1.setLesson(lesson);
//        lesson.addQuestion(question1);
//        question1.setChoices(choices);
//        choices.forEach(c -> c.setQuestion(question1));
//        question2.setLesson(lesson);
//        lesson.addQuestion(question2);
//
//        testEntityRepository.saveLesson(lesson);
//        testEntityRepository.saveChoices(choices);
//        testEntityRepository.saveQuestion(question1);
//        testEntityRepository.saveQuestion(question2);
//        entityManager.flush();
//
//        List<QuestionDTO> foundQuestions = testService.readAllQuestions();
//
//        assertThat(foundQuestions).isNotNull();
//        assertThat(foundQuestions).isNotEmpty();
//        assertThat(foundQuestions).hasSize(2);
//        assertThat(foundQuestions.get(0)).isNotEqualTo(foundQuestions.get(1));
//        assertThat(foundQuestions.get(0)).isInstanceOf(MultipleChoiceQuestionDTO.class);
//        assertThat(foundQuestions.get(0).getId()).isEqualTo(1);
//        assertThat(foundQuestions.get(0).getId()).isEqualTo(question1.getId());
//        assertThat(foundQuestions.get(0).getQuestionText()).isEqualTo(question1.getQuestionText());
//        assertThat(foundQuestions.get(0).getQuestionType()).isEqualTo(question1.getQuestionType());
//        assertThat(foundQuestions.get(1)).isInstanceOf(TrueFalseQuestionDTO.class);
//        assertThat(foundQuestions.get(1).getId()).isEqualTo(2);
//        assertThat(foundQuestions.get(1).getId()).isEqualTo(question2.getId());
//        assertThat(foundQuestions.get(1).getQuestionText()).isEqualTo(question2.getQuestionText());
//        assertThat(foundQuestions.get(1).getQuestionType()).isEqualTo(question2.getQuestionType());
//        assertThat(((TrueFalseQuestionDTO) foundQuestions.get(1)).getCorrect()).isEqualTo(question2.getCorrect());
//    }

//    @Test
//    public void testReadAllMultipleChoiceQuestions() {
//
//        List<MultipleChoiceQuestionDTO> foundNone = testService.readAllMultipleChoiceQuestions();
//        assertThat(foundNone).isNotNull();
//        assertThat(foundNone).isEmpty();
//
//        LessonEntity lesson = LessonTestUtil.createEmptyLesson();
//        testEntityRepository.saveLesson(lesson);
//        entityManager.flush();
//
//        Set<ChoiceEntity> choices = ChoiceTestUtil.createEmptyChoiceSet();
//        testEntityRepository.saveChoices(choices);
//        entityManager.flush();
//
//        MultipleChoiceQuestionEntity question1 = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
//        MultipleChoiceQuestionEntity question2 = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
//        TrueFalseQuestionEntity question3 = QuestionTestUtil.createEmptyTrueFalseQuestion();
//        testEntityRepository.saveQuestion(question1);
//        testEntityRepository.saveQuestion(question2);
//        testEntityRepository.saveQuestion(question3);
//        entityManager.flush();
//
//        question1.setLesson(lesson);
//        lesson.addQuestion(question1);
//        question1.setChoices(choices);
//        choices.forEach(c -> c.setQuestion(question1));
//        question2.setLesson(lesson);
//        lesson.addQuestion(question2);
//        question2.setChoices(choices);
//        choices.forEach(c -> c.setQuestion(question2));
//        question3.setLesson(lesson);
//        lesson.addQuestion(question3);
//
//        testEntityRepository.saveLesson(lesson);
//        testEntityRepository.saveChoices(choices);
//        testEntityRepository.saveQuestion(question1);
//        testEntityRepository.saveQuestion(question2);
//        testEntityRepository.saveQuestion(question3);
//        entityManager.flush();
//
//        List<MultipleChoiceQuestionDTO> foundQuestions = testService.readAllMultipleChoiceQuestions();
//
//        assertThat(foundQuestions).isNotNull();
//        assertThat(foundQuestions).isNotEmpty();
//        assertThat(foundQuestions).hasSize(2);
//        assertThat(foundQuestions.get(0)).isNotEqualTo(foundQuestions.get(1));
//        assertThat(foundQuestions.get(0).getId()).isEqualTo(1);
//        assertThat(foundQuestions.get(1).getId()).isEqualTo(2);
//        assertThat(foundQuestions.get(0)).isInstanceOf(MultipleChoiceQuestionDTO.class);
//        assertThat(foundQuestions.get(1)).isInstanceOf(MultipleChoiceQuestionDTO.class);
//    }

//    @Test
//    public void testReadAllTrueFalseQuestions() {
//
//        List<TrueFalseQuestionDTO> foundNone = testService.readAllTrueFalseQuestions();
//        assertThat(foundNone).isNotNull();
//        assertThat(foundNone).isEmpty();
//
//        LessonEntity lesson = LessonTestUtil.createEmptyLesson();
//        testEntityRepository.saveLesson(lesson);
//        entityManager.flush();
//
//        Set<ChoiceEntity> choices = ChoiceTestUtil.createEmptyChoiceSet();
//        testEntityRepository.saveChoices(choices);
//        entityManager.flush();
//
//        MultipleChoiceQuestionEntity question1 = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
//        TrueFalseQuestionEntity question2 = QuestionTestUtil.createEmptyTrueFalseQuestion();
//        TrueFalseQuestionEntity question3 = QuestionTestUtil.createEmptyTrueFalseQuestion();
//        testEntityRepository.saveQuestion(question1);
//        testEntityRepository.saveQuestion(question2);
//        testEntityRepository.saveQuestion(question3);
//        entityManager.flush();
//
//        question1.setLesson(lesson);
//        lesson.addQuestion(question1);
//        question1.setChoices(choices);
//        choices.forEach(c -> c.setQuestion(question1));
//        question2.setLesson(lesson);
//        lesson.addQuestion(question2);
//        question3.setLesson(lesson);
//        lesson.addQuestion(question3);
//
//        testEntityRepository.saveLesson(lesson);
//        testEntityRepository.saveChoices(choices);
//        testEntityRepository.saveQuestion(question1);
//        testEntityRepository.saveQuestion(question2);
//        testEntityRepository.saveQuestion(question3);
//        entityManager.flush();
//
//        List<TrueFalseQuestionDTO> foundQuestions = testService.readAllTrueFalseQuestions();
//
//        assertThat(foundQuestions).isNotNull();
//        assertThat(foundQuestions).isNotEmpty();
//        assertThat(foundQuestions).hasSize(2);
//        assertThat(foundQuestions.get(0)).isNotEqualTo(foundQuestions.get(1));
//        assertThat(foundQuestions.get(0).getId()).isEqualTo(2);
//        assertThat(foundQuestions.get(1).getId()).isEqualTo(3);
//        assertThat(foundQuestions.get(0)).isInstanceOf(TrueFalseQuestionDTO.class);
//        assertThat(foundQuestions.get(1)).isInstanceOf(TrueFalseQuestionDTO.class);
//    }

    @Test
    public void testFindAllQuestionsById() {

        Set<QuestionEntity> foundNone = testService.findAllQuestionsById(CommonTestUtils.toSet(1L, 2L, 3L));
        assertThat(foundNone).isNotNull();
        assertThat(foundNone).isEmpty();

        LessonEntity lesson = LessonTestUtil.createEmptyLesson();
        testEntityRepository.saveLesson(lesson);
        entityManager.flush();

        Set<ChoiceEntity> choices = ChoiceTestUtil.createEmptyChoiceSet();
        testEntityRepository.saveChoices(choices);
        entityManager.flush();

        MultipleChoiceQuestionEntity question1 = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
        TrueFalseQuestionEntity question2 = QuestionTestUtil.createEmptyTrueFalseQuestion();
        TrueFalseQuestionEntity question3 = QuestionTestUtil.createEmptyTrueFalseQuestion();
        testEntityRepository.saveQuestion(question1);
        testEntityRepository.saveQuestion(question2);
        testEntityRepository.saveQuestion(question3);
        entityManager.flush();

        question1.setLesson(lesson);
        lesson.addQuestion(question1);
        question1.setChoices(choices);
        choices.forEach(c -> c.setQuestion(question1));
        question2.setLesson(lesson);
        lesson.addQuestion(question2);
        question3.setLesson(lesson);
        lesson.addQuestion(question3);

        testEntityRepository.saveLesson(lesson);
        testEntityRepository.saveChoices(choices);
        testEntityRepository.saveQuestion(question1);
        testEntityRepository.saveQuestion(question2);
        testEntityRepository.saveQuestion(question3);
        entityManager.flush();

        Set<QuestionEntity> foundQuestions = testService.findAllQuestionsById(CommonTestUtils.toSet(1L, 2L));
        assertThat(foundQuestions).isNotNull();
        assertThat(foundQuestions).isNotEmpty();
        assertThat(foundQuestions).hasSize(2);
        foundQuestions.forEach(q -> assertThat(q instanceof MultipleChoiceQuestionEntity || q instanceof TrueFalseQuestionEntity).isTrue());

        foundQuestions = testService.findAllQuestionsById(CommonTestUtils.toSet(1L, 4L, 5L));
        assertThat(foundQuestions).isNotNull();
        assertThat(foundQuestions).isNotEmpty();
        assertThat(foundQuestions).hasSize(1);
        foundQuestions.forEach(q -> assertThat(q instanceof MultipleChoiceQuestionEntity).isTrue());

        foundQuestions = testService.findAllQuestionsById(CommonTestUtils.toSet(2L, 3L));
        assertThat(foundQuestions).isNotNull();
        assertThat(foundQuestions).isNotEmpty();
        assertThat(foundQuestions).hasSize(2);
        foundQuestions.forEach(q -> assertThat(q instanceof TrueFalseQuestionEntity).isTrue());

        foundQuestions = testService.findAllQuestionsById(CommonTestUtils.toSet(1L, 2L, 3L));
        assertThat(foundQuestions).isNotNull();
        assertThat(foundQuestions).isNotEmpty();
        assertThat(foundQuestions).hasSize(3);
        foundQuestions.forEach(q -> assertThat(q instanceof MultipleChoiceQuestionEntity || q instanceof TrueFalseQuestionEntity).isTrue());

        foundQuestions = testService.findAllQuestionsById(CommonTestUtils.toSet(4L, 5L, 6L));
        assertThat(foundQuestions).isNotNull();
        assertThat(foundQuestions).isEmpty();
    }

    @Test
    public void testFindQuestionById() {

        QuestionEntity foundNone = testService.findQuestionById(1L);
        assertThat(foundNone).isNull();

        LessonEntity lesson = LessonTestUtil.createEmptyLesson();
        testEntityRepository.saveLesson(lesson);
        entityManager.flush();

        Set<ChoiceEntity> choices = ChoiceTestUtil.createEmptyChoiceSet();
        testEntityRepository.saveChoices(choices);
        entityManager.flush();

        MultipleChoiceQuestionEntity question1 = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
        TrueFalseQuestionEntity question2 = QuestionTestUtil.createEmptyTrueFalseQuestion();
        TrueFalseQuestionEntity question3 = QuestionTestUtil.createEmptyTrueFalseQuestion();
        testEntityRepository.saveQuestion(question1);
        testEntityRepository.saveQuestion(question2);
        testEntityRepository.saveQuestion(question3);
        entityManager.flush();

        question1.setLesson(lesson);
        lesson.addQuestion(question1);
        question1.setChoices(choices);
        choices.forEach(c -> c.setQuestion(question1));
        question2.setLesson(lesson);
        lesson.addQuestion(question2);
        question3.setLesson(lesson);
        lesson.addQuestion(question3);

        testEntityRepository.saveLesson(lesson);
        testEntityRepository.saveChoices(choices);
        testEntityRepository.saveQuestion(question1);
        testEntityRepository.saveQuestion(question2);
        testEntityRepository.saveQuestion(question3);
        entityManager.flush();

        QuestionEntity foundQuestion1 = testService.findQuestionById(1L);
        QuestionEntity foundQuestion2 = testService.findQuestionById(2L);

        assertThat(foundQuestion1).isNotNull();
        assertThat(foundQuestion2).isNotNull();
        assertThat(foundQuestion1).isInstanceOf(MultipleChoiceQuestionEntity.class);
        assertThat(foundQuestion1).isEqualTo(question1);
        assertThat(foundQuestion2).isInstanceOf(TrueFalseQuestionEntity.class);
        assertThat(foundQuestion2).isEqualTo(question2);
    }

//    @Test
//    public void testUpdateMultipleChoiceQuestion() {
//
//        LessonEntity lesson = LessonTestUtil.createEmptyLesson();
//        testEntityRepository.saveLesson(lesson);
//        entityManager.flush();
//
//        Set<ChoiceEntity> choices = ChoiceTestUtil.createEmptyChoiceSet();
//        testEntityRepository.saveChoices(choices);
//        entityManager.flush();
//
//        MultipleChoiceQuestionEntity question1 = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
//        testEntityRepository.saveQuestion(question1);
//        entityManager.flush();
//
//        question1.setLesson(lesson);
//        lesson.addQuestion(question1);
//        question1.setChoices(choices);
//        choices.forEach(c -> c.setQuestion(question1));
//
//        testEntityRepository.saveLesson(lesson);
//        testEntityRepository.saveChoices(choices);
//        testEntityRepository.saveQuestion(question1);
//        entityManager.flush();
//
//        MultipleChoiceQuestionDTO dto = new MultipleChoiceQuestionDTO(
//                3L,
//                "newQuestionText",
//                QuestionType.TRUE_FALSE,
//                Optional.of(3),
//                1,
//                true,
//                4,
//                2,
//                false,
//                null
//        );
//
//        MultipleChoiceQuestionDTO updatedDTO = testService.updateMultipleChoiceQuestion(1L, dto);
//        entityManager.flush();
//
//        MultipleChoiceQuestionEntity updatedEntity = (MultipleChoiceQuestionEntity) testEntityRepository.findQuestionById(1L);
//
//        assertThat(updatedDTO).isNotNull();
//        assertThat(updatedEntity).isNotNull();
//        assertThat(updatedDTO.getId()).isEqualTo(1);
//        assertThat(updatedDTO.getId()).isEqualTo(updatedEntity.getId());
//        assertThat(updatedDTO.getId()).isNotEqualTo(dto.getId());
//        assertThat(updatedDTO.getQuestionText()).isEqualTo(updatedEntity.getQuestionText());
//        assertThat(updatedDTO.getQuestionText()).isEqualTo(dto.getQuestionText());
//        assertThat(updatedDTO.getQuestionType()).isEqualTo(updatedEntity.getQuestionType());
//        assertThat(updatedDTO.getQuestionType()).isNotEqualTo(dto.getQuestionType());
//        assertThat(updatedDTO.getLessonId()).isPresent();
//        assertThat(updatedDTO.getLessonId().get()).isEqualTo(updatedEntity.getLesson().getId());
//        assertThat(updatedDTO.getLessonId()).isNotEqualTo(dto.getLessonId());
//    }

//    @Test
//    public void testUpdateTrueFalseQuestion() {
//
//        LessonEntity lesson = LessonTestUtil.createEmptyLesson();
//        testEntityRepository.saveLesson(lesson);
//        entityManager.flush();
//
//        TrueFalseQuestionEntity question1 = QuestionTestUtil.createEmptyTrueFalseQuestion();
//        testEntityRepository.saveQuestion(question1);
//        entityManager.flush();
//
//        question1.setLesson(lesson);
//        lesson.addQuestion(question1);
//
//        testEntityRepository.saveLesson(lesson);
//        testEntityRepository.saveQuestion(question1);
//        entityManager.flush();
//
//        TrueFalseQuestionDTO dto = new TrueFalseQuestionDTO(
//                3L,
//                "newQuestionText",
//                QuestionType.MULTIPLE_CHOICE,
//                Optional.of(3),
//                2,
//                false,
//                2,
//                4,
//                false,
//                true
//        );
//
//        TrueFalseQuestionDTO updatedDTO = testService.updateTrueFalseQuestion(1L, dto);
//        entityManager.flush();
//
//        TrueFalseQuestionEntity updatedEntity = (TrueFalseQuestionEntity) testEntityRepository.findQuestionById(1L);
//
//        assertThat(updatedDTO).isNotNull();
//        assertThat(updatedEntity).isNotNull();
//        assertThat(updatedDTO.getId()).isEqualTo(1);
//        assertThat(updatedDTO.getId()).isEqualTo(updatedEntity.getId());
//        assertThat(updatedDTO.getId()).isNotEqualTo(dto.getId());
//        assertThat(updatedDTO.getQuestionText()).isEqualTo(updatedEntity.getQuestionText());
//        assertThat(updatedDTO.getQuestionText()).isEqualTo(dto.getQuestionText());
//        assertThat(updatedDTO.getQuestionType()).isEqualTo(updatedEntity.getQuestionType());
//        assertThat(updatedDTO.getQuestionType()).isNotEqualTo(dto.getQuestionType());
//        assertThat(updatedDTO.getLessonId()).isPresent();
//        assertThat(updatedDTO.getLessonId().get()).isEqualTo(updatedEntity.getLesson().getId());
//        assertThat(updatedDTO.getLessonId()).isNotEqualTo(dto.getLessonId());
//        assertThat(updatedDTO.getCorrect()).isEqualTo(updatedEntity.getCorrect());
//        assertThat(updatedDTO.getCorrect()).isEqualTo(dto.getCorrect());
//    }

    @Test
    public void testDeleteQuestionById() {

        LessonEntity lesson = LessonTestUtil.createEmptyLesson();
        testEntityRepository.saveLesson(lesson);
        entityManager.flush();

        Set<ChoiceEntity> choices = ChoiceTestUtil.createEmptyChoiceSet();
        testEntityRepository.saveChoices(choices);
        entityManager.flush();

        MultipleChoiceQuestionEntity question1 = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
        TrueFalseQuestionEntity question2 = QuestionTestUtil.createEmptyTrueFalseQuestion();
        testEntityRepository.saveQuestion(question1);
        testEntityRepository.saveQuestion(question2);
        entityManager.flush();

        question1.setLesson(lesson);
        lesson.addQuestion(question1);
        question1.setChoices(choices);
        choices.forEach(c -> c.setQuestion(question1));
        question2.setLesson(lesson);
        lesson.addQuestion(question2);

        testEntityRepository.saveLesson(lesson);
        testEntityRepository.saveChoices(choices);
        testEntityRepository.saveQuestion(question1);
        testEntityRepository.saveQuestion(question2);
        entityManager.flush();
        entityManager.clear();

        testService.deleteQuestionById(1L);
        testService.deleteQuestionById(2L);
        entityManager.flush();

        QuestionEntity foundQuestion1 = testEntityRepository.findQuestionById(1L);
        QuestionEntity foundQuestion2 = testEntityRepository.findQuestionById(2L);
        LessonEntity foundLesson = testEntityRepository.findLessonById(1);
        Set<ChoiceEntity> foundChoices = testEntityRepository.findAllChoices();

        assertThat(foundQuestion1).isNull();
        assertThat(foundQuestion2).isNull();
        assertThat(foundLesson).isNotNull();
        assertThat(foundLesson.getQuestions().stream().map(QuestionEntity::getId).toList()).doesNotContain(1L);
        assertThat(foundLesson.getQuestions().stream().map(QuestionEntity::getId).toList()).doesNotContain(2L);
        assertThat(foundChoices).isEmpty();
    }
}
