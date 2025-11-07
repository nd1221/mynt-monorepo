package com.example.mynt_finance_backend.learningPlatformTests.services.impl;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.ChoiceDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.ChoiceEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.MultipleChoiceQuestionEntity;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import com.example.mynt_finance_backend.learningPlatform.services.ChoiceService;
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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ChoiceServiceIntegrationTests implements TestContainerConfiguration {

    @Autowired
    private ChoiceService testService;

    @Autowired
    private TestEntityRepositoryFacade testEntityRepository;

    @Autowired
    private Mapper<ChoiceDTO, ChoiceEntity> choiceMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testExists() {

        ChoiceEntity choice = ChoiceTestUtil.createEmptyChoice();
        testEntityRepository.saveChoice(choice);
        entityManager.flush();

        boolean exists = testService.exists(1);
        assertThat(exists).isTrue();
    }

    @Test
    public void testCreateChoice() {

        testEntityRepository.saveQuestion(QuestionTestUtil.createEmptyMultipleChoiceQuestion());
        entityManager.flush();

        ChoiceDTO dto = ChoiceTestUtil.createEmptyChoiceDTO();
        ChoiceDTO returnedDTO = testService.createChoice(1L, dto);
        entityManager.flush();

        MultipleChoiceQuestionEntity savedQuestion = (MultipleChoiceQuestionEntity) testEntityRepository.findQuestionById(1L);
        ChoiceEntity savedChoice = testEntityRepository.findChoiceById(1);

        assertThat(returnedDTO).isNotNull();
        assertThat(returnedDTO.id()).isEqualTo(1);
        assertThat(returnedDTO.choiceText()).isEqualTo(dto.choiceText());
        assertThat(returnedDTO.isCorrect()).isEqualTo(dto.isCorrect());

        assertThat(savedChoice).isNotNull();
        assertThat(savedChoice.getId()).isEqualTo(returnedDTO.id());
        assertThat(savedChoice.getChoiceText()).isEqualTo(returnedDTO.choiceText());
        assertThat(savedChoice.getCorrect()).isEqualTo(returnedDTO.isCorrect());

        assertThat(savedQuestion).isNotNull();
        assertThat(savedQuestion.getChoices()).contains(savedChoice);
        assertThat(savedChoice.getQuestion()).isEqualTo(savedQuestion);
    }

    @Test
    public void testReadAllChoices() {

        MultipleChoiceQuestionEntity question = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
        testEntityRepository.saveQuestion(question);
        entityManager.flush();

        Set<ChoiceEntity> choiceSet = ChoiceTestUtil.createEmptyChoiceSet();
        choiceSet.forEach(choice -> choice.setQuestion(question));
        testEntityRepository.saveChoices(choiceSet);
        entityManager.flush();

        List<ChoiceDTO> savedChoices = choiceSet.stream().map(choiceMapper::mapToDTO).toList();
        List<ChoiceDTO> foundChoices = testService.readAllChoices(1L);

        assertThat(foundChoices).isNotNull();
        assertThat(foundChoices).hasSize(3);
        assertThat(foundChoices).isEqualTo(savedChoices);
    }

    @Test
    public void testUpdateChoice() {

        MultipleChoiceQuestionEntity question = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
        testEntityRepository.saveQuestion(question);
        entityManager.flush();

        ChoiceEntity originalChoice = ChoiceTestUtil.createEmptyChoice();
        originalChoice.setQuestion(question);
        testEntityRepository.saveChoice(originalChoice);
        entityManager.flush();

        ChoiceDTO toUpdate = new ChoiceDTO(null, "updated choice", false);
        ChoiceDTO updatedDTO = testService.updateChoice(1L, 1, toUpdate);
        entityManager.flush();

        ChoiceEntity updatedEntity = testEntityRepository.findChoiceById(1);

        assertThat(updatedDTO).isNotNull();
        assertThat(updatedEntity).isNotNull();
        assertThat(updatedDTO.id()).isEqualTo(updatedEntity.getId());
        assertThat(updatedDTO.choiceText()).isEqualTo(updatedEntity.getChoiceText());
        assertThat(updatedDTO.isCorrect()).isEqualTo(updatedEntity.getCorrect());
        assertThat(updatedEntity.getQuestion()).isNotNull();
        assertThat(updatedEntity.getQuestion().getId()).isEqualTo(1);
    }

    @Test
    public void testDeleteChoiceById() {

        MultipleChoiceQuestionEntity question = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
        testEntityRepository.saveQuestion(question);
        entityManager.flush();

        Set<ChoiceEntity> choiceSet = ChoiceTestUtil.createEmptyChoiceSet();
        choiceSet.forEach(choice -> choice.setQuestion(question));
        testEntityRepository.saveChoices(choiceSet);
        entityManager.flush();

        testService.deleteChoiceById(2);
        entityManager.flush();
        entityManager.clear();

        MultipleChoiceQuestionEntity savedQuestion = (MultipleChoiceQuestionEntity) testEntityRepository.findQuestionById(1L);
        Set<ChoiceEntity> foundChoices = testEntityRepository.findChoicesByQuestionId(1L);

        assertThat(savedQuestion).isNotNull();
        assertThat(foundChoices).isNotNull();
        assertThat(foundChoices).hasSize(2);
        assertThat(savedQuestion.getChoices()).hasSize(2);
        assertThat(foundChoices).isNotEqualTo(choiceSet);
        assertThat(savedQuestion.getChoices()).isEqualTo(foundChoices);
        assertThat(foundChoices.stream().map(ChoiceEntity::getId).toList()).doesNotContain(2);
    }
}
