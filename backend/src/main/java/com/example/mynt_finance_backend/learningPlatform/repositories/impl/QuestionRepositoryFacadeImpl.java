package com.example.mynt_finance_backend.learningPlatform.repositories.impl;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.MultipleChoiceQuestionEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.QuestionEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TrueFalseQuestionEntity;
import com.example.mynt_finance_backend.learningPlatform.repositories.QuestionRepository;
import com.example.mynt_finance_backend.learningPlatform.repositories.QuestionRepositoryFacade;
import com.example.mynt_finance_backend.learningPlatform.repositories.TrueFalseQuestionRepository;
import com.example.mynt_finance_backend.learningPlatform.repositories.MultipleChoiceQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class QuestionRepositoryFacadeImpl implements QuestionRepositoryFacade {

    private final MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;

    private final TrueFalseQuestionRepository trueFalseQuestionRepository;

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionRepositoryFacadeImpl(MultipleChoiceQuestionRepository multipleChoiceQuestionRepository, TrueFalseQuestionRepository trueFalseQuestionRepository, QuestionRepository questionRepository) {
        this.multipleChoiceQuestionRepository = multipleChoiceQuestionRepository;
        this.trueFalseQuestionRepository = trueFalseQuestionRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public boolean existsById(Long id) {
        return questionRepository.existsById(id);
    }

    @Override
    public boolean multipleChoiceExistsById(Long id) {
        return multipleChoiceQuestionRepository.existsById(id);
    }

    @Override
    public boolean trueFalseExistsById(Long id) {
        return trueFalseQuestionRepository.existsById(id);
    }

    @Override
    public Long getNumberOfQuestionsInCourse(int courseId) {
        return questionRepository.findNumberOfQuestionsInCourse(courseId);
    }

    @Override
    public Long getNumberOfQuestionsInSection(int sectionId) {
        return questionRepository.findNumberOfQuestionsInSection(sectionId);
    }

    @Override
    public Long getNumberOfQuestionsInLesson(int lessonId) {
        return questionRepository.findNumberOfQuestionsInLesson(lessonId);
    }

    @Override
    public MultipleChoiceQuestionEntity save(MultipleChoiceQuestionEntity questionEntity) {
        return multipleChoiceQuestionRepository.save(questionEntity);
    }

    @Override
    public TrueFalseQuestionEntity save(TrueFalseQuestionEntity questionEntity) {
        return trueFalseQuestionRepository.save(questionEntity);
    }

    @Override
    public Set<QuestionEntity> findAllQuestionsById(Set<Long> questionIds) {
        return questionIds == null ?
                null :
                Stream.concat(
                        multipleChoiceQuestionRepository.findAllById(questionIds).stream(),
                        trueFalseQuestionRepository.findAllById(questionIds).stream()
                ).collect(Collectors.toSet());
    }

    @Override
    public Optional<QuestionEntity> findQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public Optional<MultipleChoiceQuestionEntity> findMultipleChoiceById(Long id) {
        return multipleChoiceQuestionRepository.findById(id);
    }

    @Override
    public Optional<TrueFalseQuestionEntity> findTrueFalseById(Long id) {
        return trueFalseQuestionRepository.findById(id);
    }

    @Override
    public List<MultipleChoiceQuestionEntity> findAllMultipleChoiceQuestions() {
        return multipleChoiceQuestionRepository.findAll();
    }

    @Override
    public List<TrueFalseQuestionEntity> findAllTrueFalseQuestions() {
        return trueFalseQuestionRepository.findAll();
    }

    @Override
    public void deleteQuestionById(Long id) {
        questionRepository.deleteById(id);
    }

    @Override
    public Page<QuestionEntity> findAllBySpecification(Specification<QuestionEntity> spec, Pageable pageable) {
        return questionRepository.findAll(spec, pageable);
    }
}
