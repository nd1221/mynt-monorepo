package com.example.mynt_finance_backend.learningPlatformTests.services.impl;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.LessonDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.TestDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.*;
import com.example.mynt_finance_backend.learningPlatform.services.TestService;
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

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestServiceIntegrationTests implements TestContainerConfiguration {

    @Autowired
    private TestService testService;

    @Autowired
    private TestEntityRepositoryFacade testEntityRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testExists() {

        TestEntity test = TestTestEntityUtil.createEmptyTest();
        testEntityRepository.saveTest(test);
        entityManager.flush();

        boolean exists = testService.exists(1);
        assertThat(exists).isTrue();
    }

    @Test
    public void testCreateTest() {

        SectionEntity section = SectionTestUtil.createEmptySection();
        testEntityRepository.saveSection(section);
        entityManager.flush();

        TestDTO dto = TestTestEntityUtil.createFullTestDTO();
        TestDTO createdDTO = testService.createTest(1, dto);
        entityManager.flush();

        TestEntity createdEntity = testEntityRepository.findTestById(1);

        assertThat(createdDTO).isNotNull();
        assertThat(createdEntity).isNotNull();
        assertThat(createdDTO.id()).isNotEqualTo(dto.id());
        assertThat(createdDTO.id()).isEqualTo(1);
        assertThat(createdDTO.id()).isEqualTo(createdEntity.getId());
        assertThat(createdDTO.sectionId()).isPresent();
        assertThat(createdDTO.sectionId()).isNotEqualTo(dto.sectionId());
        assertThat(createdDTO.sectionId().get()).isEqualTo(createdEntity.getSection().getId());
        assertThat(createdDTO.numberOfQuestions()).isEqualTo(dto.numberOfQuestions());
        assertThat(createdDTO.numberOfQuestions()).isEqualTo(createdEntity.getNumberOfQuestions());
        assertThat(createdDTO.timeLimit()).isEqualTo(dto.timeLimit());
        assertThat(createdDTO.timeLimit()).isEqualTo(createdEntity.getTimeLimit());
        assertThat(createdDTO.questionIds()).isEmpty();
        assertThat(createdEntity.getQuestions()).isNull();
    }

    @Test
    public void testReadTest() {

        SectionEntity section = SectionTestUtil.createEmptySection();
        testEntityRepository.saveSection(section);
        entityManager.flush();

        TestEntity test = TestTestEntityUtil.createConvertedTest();
        test.setSection(section);
        section.setTest(test);
        testEntityRepository.saveQuestions(test.getQuestions());
        testEntityRepository.saveTest(test);
        entityManager.flush();

        TestDTO foundDTO = testService.readTest(1);

        assertThat(foundDTO).isNotNull();
        assertThat(foundDTO.id()).isEqualTo(test.getId());
        assertThat(foundDTO.sectionId()).isPresent();
        assertThat(foundDTO.sectionId().get()).isEqualTo(test.getSection().getId());
        assertThat(foundDTO.numberOfQuestions()).isEqualTo(test.getNumberOfQuestions());
        assertThat(foundDTO.timeLimit()).isEqualTo(test.getTimeLimit());
        assertThat(foundDTO.questionIds()).isEqualTo(test.getQuestions().stream().map(QuestionEntity::getId).collect(Collectors.toSet()));
    }

    @Test
    public void testUpdateTest() {

        SectionEntity section = SectionTestUtil.createEmptySection();
        testEntityRepository.saveSection(section);
        entityManager.flush();

        TestEntity test = TestTestEntityUtil.createConvertedTest();
        test.setSection(section);
        section.setTest(test);
        testEntityRepository.saveQuestions(test.getQuestions());
        testEntityRepository.saveTest(test);
        entityManager.flush();

        TestDTO dto = new TestDTO(
                10,
                Optional.of(3),
                20,
                1500L,
                CommonTestUtils.getUpdatedLongIds()
        );

        TestDTO updatedDTO = testService.updateTestBySectionId(1, dto);
        entityManager.flush();

        TestEntity updatedEntity = testEntityRepository.findTestById(1);

        assertThat(updatedDTO).isNotNull();
        assertThat(updatedEntity).isNotNull();
        assertThat(updatedDTO.id()).isEqualTo(updatedEntity.getId());
        assertThat(updatedDTO.id()).isNotEqualTo(dto.id());
        assertThat(updatedDTO.sectionId()).isPresent();
        assertThat(updatedDTO.sectionId().get()).isEqualTo(updatedEntity.getSection().getId());
        assertThat(updatedDTO.sectionId()).isNotEqualTo(dto.sectionId());
        assertThat(updatedDTO.numberOfQuestions()).isEqualTo(updatedEntity.getNumberOfQuestions());
        assertThat(updatedDTO.numberOfQuestions()).isEqualTo(dto.numberOfQuestions());
        assertThat(updatedDTO.timeLimit()).isEqualTo(updatedEntity.getTimeLimit());
        assertThat(updatedDTO.timeLimit()).isEqualTo(dto.timeLimit());
        assertThat(updatedDTO.questionIds()).isEqualTo(updatedEntity.getQuestions().stream().map(QuestionEntity::getId).collect(Collectors.toSet()));
        assertThat(updatedDTO.questionIds()).isNotEqualTo(dto.questionIds());
    }

    @Test
    public void testDeleteTest() {

        SectionEntity section = SectionTestUtil.createEmptySection();
        testEntityRepository.saveSection(section);
        entityManager.flush();

        TestEntity test = TestTestEntityUtil.createConvertedTest();
        testEntityRepository.saveTest(test);
        test.setSection(section);
        section.setTest(test);
        testEntityRepository.saveQuestions(test.getQuestions());
        testEntityRepository.saveTest(test);
        entityManager.flush();

        testService.deleteTest(1, 1);
        entityManager.flush();
        entityManager.clear();

        SectionEntity foundSection = testEntityRepository.findSectionById(1);
        TestEntity foundTest = testEntityRepository.findTestById(1);
        Set<QuestionEntity> foundQuestions = testEntityRepository.findAllQuestions();

        assertThat(foundSection).isNotNull();
        assertThat(foundSection.getTest()).isNull();
        assertThat(foundTest).isNull();
        assertThat(foundQuestions).isNotNull();
        assertThat(foundQuestions).isNotEmpty();
        assertThat(foundQuestions).hasSize(4);
    }

    @Test
    public void testAddQuestion() {

        MultipleChoiceQuestionEntity question1 = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
        TrueFalseQuestionEntity question2 = QuestionTestUtil.createEmptyTrueFalseQuestion();
        testEntityRepository.saveQuestion(question1);
        testEntityRepository.saveQuestion(question2);
        entityManager.flush();

        TestEntity test = TestTestEntityUtil.createEmptyTest();
        testEntityRepository.saveTest(test);
        entityManager.flush();
        entityManager.clear();

        TestDTO updatedDTO = testService.addQuestion(1 , 1L);
        TestEntity updatedEntity = testEntityRepository.findTestById(1);
        MultipleChoiceQuestionEntity updatedQuestion1 = (MultipleChoiceQuestionEntity) testEntityRepository.findQuestionById(1L);
        entityManager.flush();

        assertThat(updatedDTO.id()).isEqualTo(updatedEntity.getId());
        assertThat(updatedDTO.questionIds()).contains(updatedQuestion1.getId());
        assertThat(updatedEntity.getQuestions()).contains(updatedQuestion1);

        updatedDTO = testService.addQuestion(1, 2L);
        TrueFalseQuestionEntity updatedQuestion2 = (TrueFalseQuestionEntity) testEntityRepository.findQuestionById(2L);
        entityManager.flush();

        assertThat(updatedDTO.id()).isEqualTo(updatedEntity.getId());
        assertThat(updatedDTO.questionIds()).contains(updatedQuestion1.getId());
        assertThat(updatedEntity.getQuestions()).contains(updatedQuestion1);
        assertThat(updatedDTO.questionIds()).contains(updatedQuestion2.getId());
        assertThat(updatedEntity.getQuestions()).contains(updatedQuestion2);
    }

    @Test
    public void testRemoveQuestion() {

        MultipleChoiceQuestionEntity question1 = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
        TrueFalseQuestionEntity question2 = QuestionTestUtil.createEmptyTrueFalseQuestion();
        testEntityRepository.saveQuestion(question1);
        testEntityRepository.saveQuestion(question2);
        entityManager.flush();

        TestEntity test = TestTestEntityUtil.createEmptyTest();
        testEntityRepository.saveTest(test);
        entityManager.flush();

        test.addQuestion(question1);
        test.addQuestion(question2);
        testEntityRepository.saveTest(test);
        entityManager.flush();

        TestDTO updatedDTO = testService.removeQuestion(1 , 1L);
        TestEntity updatedEntity = testEntityRepository.findTestById(1);
        MultipleChoiceQuestionEntity updatedQuestion1 = (MultipleChoiceQuestionEntity) testEntityRepository.findQuestionById(1L);
        TrueFalseQuestionEntity updatedQuestion2 = (TrueFalseQuestionEntity) testEntityRepository.findQuestionById(2L);
        entityManager.flush();

        assertThat(updatedDTO.id()).isEqualTo(updatedEntity.getId());
        assertThat(updatedQuestion1).isNotNull();
        assertThat(updatedDTO.questionIds()).doesNotContain(question1.getId());
        assertThat(updatedEntity.getQuestions()).doesNotContain(question1);
        assertThat(updatedQuestion2).isNotNull();
        assertThat(updatedDTO.questionIds()).contains(updatedQuestion2.getId());
        assertThat(updatedEntity.getQuestions()).contains(updatedQuestion2);

        updatedDTO = testService.removeQuestion(1, 2L);
        entityManager.flush();

        updatedQuestion1 = (MultipleChoiceQuestionEntity) testEntityRepository.findQuestionById(1L);
        updatedQuestion2 = (TrueFalseQuestionEntity) testEntityRepository.findQuestionById(2L);

        assertThat(updatedDTO.id()).isEqualTo(updatedEntity.getId());
        assertThat(updatedQuestion1).isNotNull();
        assertThat(updatedQuestion2).isNotNull();
        assertThat(updatedDTO.questionIds()).doesNotContain(question1.getId());
        assertThat(updatedEntity.getQuestions()).doesNotContain(question1);
        assertThat(updatedDTO.questionIds()).doesNotContain(question2.getId());
        assertThat(updatedEntity.getQuestions()).doesNotContain(question2);
    }

    @Test
    public void testHasQuestion() {

        MultipleChoiceQuestionEntity question1 = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
        TrueFalseQuestionEntity question2 = QuestionTestUtil.createEmptyTrueFalseQuestion();
        testEntityRepository.saveQuestion(question1);
        testEntityRepository.saveQuestion(question2);
        entityManager.flush();

        TestEntity test = TestTestEntityUtil.createEmptyTest();
        testEntityRepository.saveTest(test);
        entityManager.flush();

        boolean testExists = testService.exists(1);
        boolean noQuestion1 = testService.hasQuestion(1, 1L);
        boolean noQuestion2 = testService.hasQuestion(1, 2L);
        assertThat(testExists).isTrue();
        assertThat(noQuestion1).isFalse();
        assertThat(noQuestion2).isFalse();

        test.addQuestion(question1);
        test.addQuestion(question2);
        testEntityRepository.saveTest(test);
        entityManager.flush();

        boolean hasQuestion1 = testService.hasQuestion(1, 1L);
        boolean hasQuestion2 = testService.hasQuestion(1, 2L);
        boolean hasQuestion3 = testService.hasQuestion(1, 3L);
        boolean hasQuestion4 = testService.hasQuestion(1, 4L);

        assertThat(hasQuestion1).isTrue();
        assertThat(hasQuestion2).isTrue();
        assertThat(hasQuestion3).isFalse();
        assertThat(hasQuestion4).isFalse();
    }
}
