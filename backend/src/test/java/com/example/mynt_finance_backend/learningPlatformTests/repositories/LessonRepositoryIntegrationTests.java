package com.example.mynt_finance_backend.learningPlatformTests.repositories;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.LessonEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.MultipleChoiceQuestionEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TrueFalseQuestionEntity;
import com.example.mynt_finance_backend.learningPlatform.repositories.LessonRepository;
import com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries.TestContainerConfiguration;
import com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries.TestEntityRepositoryFacade;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LessonRepositoryIntegrationTests implements TestContainerConfiguration {

    @Autowired
    private LessonRepository testRepository;

    @Autowired
    private TestEntityRepositoryFacade testEntityRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testLessonContainsQuestion() {

        LessonEntity lesson = LessonTestUtil.createEmptyLesson();
        testEntityRepository.saveLesson(lesson);
        entityManager.flush();

        boolean lessonExists = testRepository.existsById(1);
        boolean lessonContainsQuestion1 = testRepository.lessonContainsQuestion(1, 1L);
        boolean lessonContainsQuestion2 = testRepository.lessonContainsQuestion(1, 2L);
        assertThat(lessonExists).isTrue();
        assertThat(lessonContainsQuestion1).isFalse();
        assertThat(lessonContainsQuestion2).isFalse();

        MultipleChoiceQuestionEntity question1 = QuestionTestUtil.createEmptyMultipleChoiceQuestion();
        TrueFalseQuestionEntity question2 = QuestionTestUtil.createEmptyTrueFalseQuestion();
        testEntityRepository.saveQuestion(question1);
        testEntityRepository.saveQuestion(question2);
        entityManager.flush();

        question1.setLesson(lesson);
        lesson.addQuestion(question1);
        testEntityRepository.saveLesson(lesson);
        testEntityRepository.saveQuestion(question1);
        entityManager.flush();

        lessonContainsQuestion1 = testRepository.lessonContainsQuestion(1, 1L);
        lessonContainsQuestion2 = testRepository.lessonContainsQuestion(1, 2L);
        LessonEntity savedLesson = testEntityRepository.findLessonById(1);

        assertThat(lessonContainsQuestion1).isTrue();
        assertThat(lessonContainsQuestion2).isFalse();
        assertThat(savedLesson.getQuestions()).contains(question1);
        assertThat(savedLesson.getQuestions()).doesNotContain(question2);
    }
}
