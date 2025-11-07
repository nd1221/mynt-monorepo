package com.example.mynt_finance_backend.learningPlatform.services;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.MultipleChoiceQuestionDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.QuestionDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.TrueFalseQuestionDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.QuestionEntity;
import com.example.mynt_finance_backend.util.baseInterfaces.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Set;

public interface QuestionService extends Service<Long> {

    boolean multipleChoiceQuestionExists(Long id);

    boolean trueFalseQuestionExists(Long id);

    // CREATE
    MultipleChoiceQuestionDTO createQuestion(MultipleChoiceQuestionDTO multipleChoiceQuestionDTO);

    TrueFalseQuestionDTO createQuestion(TrueFalseQuestionDTO trueFalseQuestionDTO);

    // READ
    QuestionDTO readQuestionById(Long id);

    List<QuestionDTO> readAllQuestions();

    List<MultipleChoiceQuestionDTO> readAllMultipleChoiceQuestions();

    List<TrueFalseQuestionDTO> readAllTrueFalseQuestions();

    Set<QuestionEntity> findAllQuestionsById(Set<Long> questionIds);

    QuestionEntity findQuestionById(Long id);

    Long getNumberOfQuestionsInCourse(int courseId);

    Long getNumberOfQuestionsInSection(int sectionId);

    Long getNumberOfQuestionsInLesson(int lessonId);

    Page<QuestionEntity> findAllBySpecification(Specification<QuestionEntity> spec, Pageable pageable);

    // UPDATE
    MultipleChoiceQuestionDTO updateMultipleChoiceQuestion(Long id, MultipleChoiceQuestionDTO questionDTO);

    TrueFalseQuestionDTO updateTrueFalseQuestion(Long id, TrueFalseQuestionDTO questionDTO);

    QuestionDTO mapQuestion(QuestionEntity question, boolean locked);

    // DELETE
    void deleteQuestionById(Long id);
}
