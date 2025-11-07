package com.example.mynt_finance_backend.learningPlatformTests.repositories;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.MultipleChoiceQuestionEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TestEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TrueFalseQuestionEntity;
import com.example.mynt_finance_backend.learningPlatform.repositories.TestRepository;
import com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries.TestContainerConfiguration;
import com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries.TestEntityRepositoryFacade;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.QuestionTestUtil;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.TestTestEntityUtil;
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

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestRepositoryIntegrationTests implements TestContainerConfiguration {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private TestEntityRepositoryFacade testEntityRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testTestContainsQuestion() {

        TestEntity test = TestTestEntityUtil.createEmptyTest();
        testEntityRepository.saveTest(test);
        entityManager.flush();

        boolean testContainsQuestion1 = testRepository.testContainsQuestion(1, 1L);
        boolean testContainsQuestion2 = testRepository.testContainsQuestion(1, 2L);
        assertThat(testContainsQuestion1).isFalse();
        assertThat(testContainsQuestion2).isFalse();

        MultipleChoiceQuestionEntity question1 = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
        TrueFalseQuestionEntity question2 = QuestionTestUtil.createEmptyTrueFalseQuestion();
        testEntityRepository.saveQuestion(question1);
        testEntityRepository.saveQuestion(question2);
        entityManager.flush();

        test.addQuestion(question1);
        testEntityRepository.saveTest(test);
        testEntityRepository.saveQuestion(question1);
        entityManager.flush();

        testContainsQuestion1 = testRepository.testContainsQuestion(1, 1L);
        testContainsQuestion2 = testRepository.testContainsQuestion(1, 2L);
        TestEntity savedTest = testEntityRepository.findTestById(1);

        assertThat(testContainsQuestion1).isTrue();
        assertThat(testContainsQuestion2).isFalse();
        assertThat(savedTest.getQuestions()).contains(question1);
        assertThat(savedTest.getQuestions()).doesNotContain(question2);
    }
}
