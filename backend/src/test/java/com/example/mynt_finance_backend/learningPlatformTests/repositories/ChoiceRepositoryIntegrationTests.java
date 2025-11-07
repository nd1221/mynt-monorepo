package com.example.mynt_finance_backend.learningPlatformTests.repositories;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.ChoiceEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.MultipleChoiceQuestionEntity;
import com.example.mynt_finance_backend.learningPlatform.repositories.ChoiceRepository;
import com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries.TestContainerConfiguration;
import com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries.TestEntityRepositoryFacade;
import com.example.mynt_finance_backend.learningPlatformTests.testUtils.ChoiceTestUtil;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ChoiceRepositoryIntegrationTests implements TestContainerConfiguration {

    @Autowired
    private ChoiceRepository testRepository;

    @Autowired
    private TestEntityRepositoryFacade testEntityRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testFindChoicesByQuestionId() {

        MultipleChoiceQuestionEntity question = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
        testEntityRepository.saveQuestion(question);
        entityManager.flush();

        List<ChoiceEntity> foundNone = testRepository.findChoicesByQuestionId(1L);
        assertThat(foundNone).isNotNull();
        assertThat(foundNone).isEmpty();

        Set<ChoiceEntity> choices = ChoiceTestUtil.createEmptyChoiceSet();
        testEntityRepository.saveChoices(choices);
        entityManager.flush();

        choices.forEach(c -> {
            if (c.getId() != 2) {
                c.setQuestion(question);
                question.addChoice(c);
            }
        });
        testEntityRepository.saveQuestion(question);
        testEntityRepository.saveChoices(choices);
        entityManager.flush();

        Set<ChoiceEntity> questionChoices = new HashSet<>(testRepository.findChoicesByQuestionId(1L));
        Set<ChoiceEntity> allChoices = testEntityRepository.findAllChoices();
        MultipleChoiceQuestionEntity savedQuestion = (MultipleChoiceQuestionEntity) testEntityRepository.findQuestionById(1L);

        assertThat(questionChoices).isNotNull();
        assertThat(questionChoices).isNotEmpty();
        assertThat(questionChoices).hasSize(2);
        assertThat(allChoices).isNotNull();
        assertThat(allChoices).isNotEmpty();
        assertThat(allChoices).hasSize(3);
        assertThat(allChoices).isNotEqualTo(questionChoices);
        assertThat(allChoices).isEqualTo(choices);
        assertThat(savedQuestion).isNotNull();
        assertThat(savedQuestion.getChoices()).isNotNull();
        assertThat(savedQuestion.getChoices()).isEqualTo(questionChoices);
    }
}
