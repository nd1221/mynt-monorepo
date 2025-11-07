package com.example.mynt_finance_backend.learningPlatformTests.repositories;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.*;
import com.example.mynt_finance_backend.learningPlatform.repositories.QuestionRepositoryFacade;
import com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries.TestContainerConfiguration;
import com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries.TestEntityRepositoryFacade;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.*;
import com.example.mynt_finance_backend.progressTracking.domain.entities.TestProgressTracker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class QuestionRepositoryFacadeIntegrationTests implements TestContainerConfiguration {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private QuestionRepositoryFacade testRepository;

    @Autowired
    private TestEntityRepositoryFacade testEntityRepositoryFacade;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testCreateAndFindEmptyTrueFalseQuestionById() {

        TrueFalseQuestionEntity testQuestion = QuestionTestUtil.createEmptyTrueFalseQuestion();
        testRepository.save(testQuestion);
        entityManager.flush();
        QuestionEntity savedQuestion = testRepository.findQuestionById(1L).orElse(null);
        assertThat(savedQuestion).isNotNull();
        assertThat(savedQuestion.equals(testQuestion)).isTrue();
    }

    @Test
    public void testCreateAndFindFullTrueFalseQuestionById() {

        TrueFalseQuestionEntity testQuestion = QuestionTestUtil.createEmptyTrueFalseQuestion();
        setChildren(testQuestion);

        testRepository.save(testQuestion);
        entityManager.flush();

        QuestionEntity savedQuestion = testRepository.findQuestionById(1L).orElse(null);
        assertThat(savedQuestion).isNotNull();
        assertThat(savedQuestion.equals(testQuestion)).isTrue();
    }

    @Test
    public void testCreateAndFindEmptyMultipleChoiceQuestionById() {

        MultipleChoiceQuestionEntity testQuestion = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
        testRepository.save(testQuestion);
        entityManager.flush();
        QuestionEntity savedQuestion = testRepository.findQuestionById(1L).orElse(null);
        assertThat(savedQuestion).isNotNull();
        assertThat(savedQuestion.equals(testQuestion)).isTrue();
    }

    @Test
    public void testCreateAndFindFullMultipleChoiceQuestionById() {

        MultipleChoiceQuestionEntity testQuestion = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
        setChildren(testQuestion);
        setChoices(testQuestion);

        testRepository.save(testQuestion);
        entityManager.flush();

        QuestionEntity savedQuestion = testRepository.findQuestionById(1L).orElse(null);
        assertThat(savedQuestion).isNotNull();
        assertThat(savedQuestion.equals(testQuestion)).isTrue();
    }

    @Test
    public void testTrueFalseQuestionExistsById() {

        TrueFalseQuestionEntity testQuestion = QuestionTestUtil.createEmptyTrueFalseQuestion();
        testRepository.save(testQuestion);
        entityManager.flush();

        boolean questionExists = testRepository.existsById(1L);
        boolean trueFalseExists = testRepository.trueFalseExistsById(1L);
        boolean multipleChoiceExists = testRepository.multipleChoiceExistsById(1L);

        assertThat(questionExists).isTrue();
        assertThat(trueFalseExists).isTrue();
        assertThat(multipleChoiceExists).isFalse();
    }

    @Test
    public void testMultipleChoiceQuestionExistsById() {

        MultipleChoiceQuestionEntity testQuestion = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
        testRepository.save(testQuestion);
        entityManager.flush();

        boolean questionExists = testRepository.existsById(1L);
        boolean trueFalseExists = testRepository.trueFalseExistsById(1L);
        boolean multipleChoiceExists = testRepository.multipleChoiceExistsById(1L);

        assertThat(questionExists).isTrue();
        assertThat(trueFalseExists).isFalse();
        assertThat(multipleChoiceExists).isTrue();
    }

    @Test
    public void testFindAllQuestionsByIdSet() {

        Set<TrueFalseQuestionEntity> trueFalseQuestions = QuestionTestUtil.createEmptyTrueFalseQuestionSet();
        Set<MultipleChoiceQuestionEntity> multipleChoiceQuestions = QuestionTestUtil.createEmptyMultipleChoiceQuestionSet();

        trueFalseQuestions.forEach(testRepository::save);
        multipleChoiceQuestions.forEach(testRepository::save);
        entityManager.flush();

        Set<Long> questionIds = Set.of(1L, 3L, 5L, 6L);

        QuestionEntity unwantedTrueFalseQuestion = testRepository.findQuestionById(2L).orElse(null);
        QuestionEntity unwantedMultipleChoiceQuestion = testRepository.findQuestionById(4L).orElse(null);
        Set<QuestionEntity> foundQuestions = testRepository.findAllQuestionsById(questionIds);

        assertThat(foundQuestions).isNotNull();
        assertThat(foundQuestions).hasSize(4);
        assertThat(unwantedTrueFalseQuestion).isNotNull();
        assertThat(unwantedMultipleChoiceQuestion).isNotNull();
    }

    @Test
    public void testFindAllEmptyTrueFalseQuestions() {

        Set<TrueFalseQuestionEntity> trueFalseQuestions = QuestionTestUtil.createEmptyTrueFalseQuestionSet();
        Set<MultipleChoiceQuestionEntity> multipleChoiceQuestions = QuestionTestUtil.createEmptyMultipleChoiceQuestionSet();

        trueFalseQuestions.forEach(testRepository::save);
        multipleChoiceQuestions.forEach(testRepository::save);
        entityManager.flush();

        Set<TrueFalseQuestionEntity> foundTrueFalseQuestions = new HashSet<>(testRepository.findAllTrueFalseQuestions());
        assertThat(foundTrueFalseQuestions.equals(trueFalseQuestions)).isTrue();
    }

    @Test
    public void testFindAllEmptyMultipleChoiceQuestions() {

        Set<TrueFalseQuestionEntity> trueFalseQuestions = QuestionTestUtil.createEmptyTrueFalseQuestionSet();
        Set<MultipleChoiceQuestionEntity> multipleChoiceQuestions = QuestionTestUtil.createEmptyMultipleChoiceQuestionSet();

        trueFalseQuestions.forEach(testRepository::save);
        multipleChoiceQuestions.forEach(testRepository::save);
        entityManager.flush();

        Set<MultipleChoiceQuestionEntity> foundMultipleChoiceQuestions = new HashSet<>(testRepository.findAllMultipleChoiceQuestions());
        assertThat(foundMultipleChoiceQuestions.equals(multipleChoiceQuestions)).isTrue();
    }

    @Test
    public void testFindAllFullTrueFalseQuestions() {

        Set<TrueFalseQuestionEntity> trueFalseQuestions = QuestionTestUtil.createEmptyTrueFalseQuestionSet();
        Set<MultipleChoiceQuestionEntity> multipleChoiceQuestions = QuestionTestUtil.createEmptyMultipleChoiceQuestionSet();

        trueFalseQuestions.forEach(this::setChildren);
        multipleChoiceQuestions.forEach(this::setChildren);
        multipleChoiceQuestions.forEach(this::setChoices);

        trueFalseQuestions.forEach(testRepository::save);
        multipleChoiceQuestions.forEach(testRepository::save);
        entityManager.flush();

        Set<TrueFalseQuestionEntity> foundTrueFalseQuestions = new HashSet<>(testRepository.findAllTrueFalseQuestions());
        assertThat(foundTrueFalseQuestions.equals(trueFalseQuestions)).isTrue();
    }

    @Test
    public void testFindAllFullMultipleChoiceQuestions() {

        Set<TrueFalseQuestionEntity> trueFalseQuestions = QuestionTestUtil.createEmptyTrueFalseQuestionSet();
        Set<MultipleChoiceQuestionEntity> multipleChoiceQuestions = QuestionTestUtil.createEmptyMultipleChoiceQuestionSet();

        trueFalseQuestions.forEach(this::setChildren);
        multipleChoiceQuestions.forEach(this::setChildren);
        multipleChoiceQuestions.forEach(this::setChoices);

        trueFalseQuestions.forEach(testRepository::save);
        multipleChoiceQuestions.forEach(testRepository::save);
        entityManager.flush();

        Set<MultipleChoiceQuestionEntity> foundMultipleChoiceQuestions = new HashSet<>(testRepository.findAllMultipleChoiceQuestions());
        assertThat(foundMultipleChoiceQuestions.equals(multipleChoiceQuestions)).isTrue();
    }

    @Test
    public void testFindMultipleTypesInSameSession() {

        Set<TrueFalseQuestionEntity> trueFalseQuestions = QuestionTestUtil.createEmptyTrueFalseQuestionSet();
        Set<MultipleChoiceQuestionEntity> multipleChoiceQuestions = QuestionTestUtil.createEmptyMultipleChoiceQuestionSet();

        trueFalseQuestions.forEach(this::setChildren);
        multipleChoiceQuestions.forEach(this::setChildren);
        multipleChoiceQuestions.forEach(this::setChoices);

        trueFalseQuestions.forEach(testRepository::save);
        multipleChoiceQuestions.forEach(testRepository::save);
        entityManager.flush();

        QuestionEntity foundTrueFalseQuestion = testRepository.findQuestionById(1L).orElse(null);
        QuestionEntity foundMultipleChoiceQuestion = testRepository.findQuestionById(4L).orElse(null);
        assertThat(foundTrueFalseQuestion).isNotNull();
        assertInstanceOf(TrueFalseQuestionEntity.class, foundTrueFalseQuestion);
        assertThat(foundMultipleChoiceQuestion).isNotNull();
        assertInstanceOf(MultipleChoiceQuestionEntity.class, foundMultipleChoiceQuestion);
    }

    @Test
    public void testDeleteEmptyTrueFalseQuestionById() {

        TrueFalseQuestionEntity testQuestion = QuestionTestUtil.createEmptyTrueFalseQuestion();
        testRepository.save(testQuestion);
        entityManager.flush();
        testRepository.deleteQuestionById(1L);
        entityManager.flush();
        QuestionEntity deletedQuestion = testRepository.findQuestionById(1L).orElse(null);
        assertThat(deletedQuestion).isNull();
    }

    @Test
    public void testDeleteEmptyMultipleChoiceQuestionById() {

        MultipleChoiceQuestionEntity testQuestion = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
        testRepository.save(testQuestion);
        entityManager.flush();
        testRepository.deleteQuestionById(1L);
        entityManager.flush();
        QuestionEntity deletedQuestion = testRepository.findQuestionById(1L).orElse(null);
        assertThat(deletedQuestion).isNull();
    }

    @Test
    public void testDeleteFullTrueFalseQuestionById() {

        TrueFalseQuestionEntity testQuestion = QuestionTestUtil.createEmptyTrueFalseQuestion();
        setChildren(testQuestion);

        testRepository.save(testQuestion);
        entityManager.flush();
        testRepository.deleteQuestionById(1L);
        entityManager.flush();

        QuestionEntity deletedQuestion = testRepository.findQuestionById(1L).orElse(null);
        assertThat(deletedQuestion).isNull();
    }

    @Test
    public void testDeleteFullMultipleChoiceQuestionById() {

        MultipleChoiceQuestionEntity testQuestion = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
        setChildren(testQuestion);
        setChoices(testQuestion);

        testRepository.save(testQuestion);
        entityManager.flush();
        testRepository.deleteQuestionById(1L);
        entityManager.flush();

        QuestionEntity deletedQuestion = testRepository.findQuestionById(1L).orElse(null);
        assertThat(deletedQuestion).isNull();
    }

    @Test
    public void testDeleteMultipleQuestions() {

        Set<TrueFalseQuestionEntity> trueFalseQuestions = QuestionTestUtil.createEmptyTrueFalseQuestionSet();
        Set<MultipleChoiceQuestionEntity> multipleChoiceQuestions = QuestionTestUtil.createEmptyMultipleChoiceQuestionSet();

        trueFalseQuestions.forEach(this::setChildren);
        multipleChoiceQuestions.forEach(this::setChildren);
        multipleChoiceQuestions.forEach(this::setChoices);

        trueFalseQuestions.forEach(testRepository::save);
        multipleChoiceQuestions.forEach(testRepository::save);
        entityManager.flush();
        testRepository.deleteQuestionById(2L);
        testRepository.deleteQuestionById(5L);
        entityManager.flush();

        QuestionEntity deletedTrueFalseQuestion = testRepository.findQuestionById(2L).orElse(null);
        QuestionEntity deletedMultipleChoiceQuestion = testRepository.findQuestionById(5L).orElse(null);
        Set<TrueFalseQuestionEntity> foundTrueFalseQuestions = new HashSet<>(testRepository.findAllTrueFalseQuestions());
        Set<MultipleChoiceQuestionEntity> foundMultipleChoiceQuestions = new HashSet<>(testRepository.findAllMultipleChoiceQuestions());

        assertThat(deletedTrueFalseQuestion).isNull();
        assertThat(deletedMultipleChoiceQuestion).isNull();
        assertThat(foundTrueFalseQuestions).hasSize(2);
        assertThat(foundMultipleChoiceQuestions).hasSize(2);
    }

    private void setChildren(QuestionEntity question) {

        LessonEntity lesson = LessonTestUtil.createEmptyLesson();
        Set<TestEntity> tests = TestTestEntityUtil.createEmptyTestSet();
        tests.forEach(this::setTestSection);

        ChildTestUtil.addChildren(lesson, question::setLesson);

        testEntityRepositoryFacade.saveLesson(lesson);
        testEntityRepositoryFacade.saveTests(tests);
    }

    private void setTestSection(TestEntity test) {
        SectionEntity section = SectionTestUtil.createEmptySection();
        test.setSection(section);
        testEntityRepositoryFacade.saveSection(section);
        entityManager.flush();
    }

    private void setChoices(MultipleChoiceQuestionEntity question) {

        Set<ChoiceEntity> choices = ChoiceTestUtil.createEmptyChoiceSet();
        ChildTestUtil.addChildren(choices, question::setChoices);
        testEntityRepositoryFacade.saveChoices(choices);
    }
}
