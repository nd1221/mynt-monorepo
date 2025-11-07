package com.example.mynt_finance_backend.learningPlatform.repositories;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.QuestionEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.MultipleChoiceQuestionEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TrueFalseQuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface QuestionRepositoryFacade {

    boolean existsById(Long id);

    boolean multipleChoiceExistsById(Long id);

    boolean trueFalseExistsById(Long id);

    Long getNumberOfQuestionsInCourse(int courseId);

    Long getNumberOfQuestionsInSection(int sectionId);

    Long getNumberOfQuestionsInLesson(int lessonId);

    MultipleChoiceQuestionEntity save(MultipleChoiceQuestionEntity questionEntity);

    TrueFalseQuestionEntity save(TrueFalseQuestionEntity questionEntity);

    Set<QuestionEntity> findAllQuestionsById(Set<Long> questionIds);

    Optional<QuestionEntity> findQuestionById(Long id);

    Optional<MultipleChoiceQuestionEntity> findMultipleChoiceById(Long id);

    Optional<TrueFalseQuestionEntity> findTrueFalseById(Long id);

    List<MultipleChoiceQuestionEntity> findAllMultipleChoiceQuestions();

    List<TrueFalseQuestionEntity> findAllTrueFalseQuestions();

    void deleteQuestionById(Long id);

    Page<QuestionEntity> findAllBySpecification(Specification<QuestionEntity> spec, Pageable pageable);
}
