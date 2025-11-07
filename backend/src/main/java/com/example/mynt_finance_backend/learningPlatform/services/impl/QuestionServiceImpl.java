package com.example.mynt_finance_backend.learningPlatform.services.impl;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.MultipleChoiceQuestionDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.QuestionDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.TrueFalseQuestionDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.MultipleChoiceQuestionEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.QuestionEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TrueFalseQuestionEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entityEnums.QuestionType;
import com.example.mynt_finance_backend.learningPlatform.repositories.QuestionRepositoryFacade;
import com.example.mynt_finance_backend.learningPlatform.mappers.GenericQuestionMapper;
import com.example.mynt_finance_backend.learningPlatform.services.QuestionService;
import com.example.mynt_finance_backend.learningPlatform.services.intermediaries.LearningPlatformServicesFacade;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepositoryFacade questionRepository;

    private final GenericQuestionMapper genericQuestionMapper;

    private final LearningPlatformServicesFacade learningPlatformServicesFacade;

    @Autowired
    public QuestionServiceImpl(QuestionRepositoryFacade questionRepository, GenericQuestionMapper genericQuestionMapper, @Lazy LearningPlatformServicesFacade learningPlatformServicesFacade) {
        this.questionRepository = questionRepository;
        this.genericQuestionMapper = genericQuestionMapper;
        this.learningPlatformServicesFacade = learningPlatformServicesFacade;
    }

    @Override
    public boolean exists(Long id) {
        return questionRepository.existsById(id);
    }

    @Override
    public boolean multipleChoiceQuestionExists(Long id) {
        return questionRepository.multipleChoiceExistsById(id);
    }

    @Override
    public boolean trueFalseQuestionExists(Long id) {
        return questionRepository.trueFalseExistsById(id);
    }

    // CREATE
    @Override
    public MultipleChoiceQuestionDTO createQuestion(MultipleChoiceQuestionDTO multipleChoiceQuestionDTO) {
        MultipleChoiceQuestionEntity questionEntity = genericQuestionMapper.mapToEntity(multipleChoiceQuestionDTO);
        return genericQuestionMapper.mapToDTO(questionRepository.save(questionEntity), false);
    }

    @Override
    public TrueFalseQuestionDTO createQuestion(TrueFalseQuestionDTO trueFalseQuestionDTO) {
        TrueFalseQuestionEntity questionEntity = genericQuestionMapper.mapToEntity(trueFalseQuestionDTO);
        return genericQuestionMapper.mapToDTO(questionRepository.save(questionEntity), false);
    }

    @Override
    public QuestionDTO readQuestionById(Long id) {

        QuestionEntity question = questionRepository.findQuestionById(id).get(); // Already checked for existence in controller

        if (question.getQuestionType().equals(QuestionType.TRUE_FALSE)) {
            return genericQuestionMapper.mapToDTO((TrueFalseQuestionEntity) question, false);
        } else if (question.getQuestionType().equals(QuestionType.MULTIPLE_CHOICE)) {
            return genericQuestionMapper.mapToDTO((MultipleChoiceQuestionEntity) question, false);
        } else {
            return null;
        }
    }

    @Override
    public List<QuestionDTO> readAllQuestions() {
        return Stream.concat(
                readAllMultipleChoiceQuestions().stream(),
                readAllTrueFalseQuestions().stream()
        ).toList();
    }

    @Override
    public List<MultipleChoiceQuestionDTO> readAllMultipleChoiceQuestions() {
        return questionRepository.findAllMultipleChoiceQuestions().stream().map(mcq -> genericQuestionMapper.mapToDTO(mcq, false)).toList();
    }

    @Override
    public List<TrueFalseQuestionDTO> readAllTrueFalseQuestions() {
        return questionRepository.findAllTrueFalseQuestions().stream().map(tfq -> genericQuestionMapper.mapToDTO(tfq, false)).toList();
    }

    @Override
    public Set<QuestionEntity> findAllQuestionsById(Set<Long> questionIds) {
        return questionRepository.findAllQuestionsById(questionIds);
    }

    @Override
    public QuestionEntity findQuestionById(Long id) {
        return questionRepository.findQuestionById(id).orElse(null);
    }

    @Override
    public Long getNumberOfQuestionsInCourse(int courseId) {
        return questionRepository.getNumberOfQuestionsInCourse(courseId);
    }

    @Override
    public Long getNumberOfQuestionsInSection(int sectionId) {
        return questionRepository.getNumberOfQuestionsInSection(sectionId);
    }

    @Override
    public Long getNumberOfQuestionsInLesson(int lessonId) {
        return questionRepository.getNumberOfQuestionsInLesson(lessonId);
    }

    @Override
    public Page<QuestionEntity> findAllBySpecification(Specification<QuestionEntity> spec, Pageable pageable) {
        return questionRepository.findAllBySpecification(spec, pageable);
    }

    // UPDATE
    @Override
    public MultipleChoiceQuestionDTO updateMultipleChoiceQuestion(Long id, MultipleChoiceQuestionDTO questionDTO) {

        MultipleChoiceQuestionEntity existingEntity = questionRepository.findMultipleChoiceById(id).get();
        updateFieldIfNotNull(existingEntity::setQuestionText, questionDTO.getQuestionText());
        return genericQuestionMapper.mapToDTO(questionRepository.save(existingEntity), false);
    }

    @Override
    public TrueFalseQuestionDTO updateTrueFalseQuestion(Long id, TrueFalseQuestionDTO questionDTO) {

        TrueFalseQuestionEntity existingEntity = questionRepository.findTrueFalseById(id).get();

        updateFieldIfNotNull(existingEntity::setQuestionText, questionDTO.getQuestionText());
        updateFieldIfNotNull(existingEntity::setCorrect, questionDTO.getCorrect());

        return genericQuestionMapper.mapToDTO(questionRepository.save(existingEntity), false);
    }

    // Must use instanceof checks as opposed to checking question.getQuestionType() due to how Hibernate proxies work
    // with methods of a base class that are not overridden in the child class. Because question.getQuestionType() is
    // not overridden in MultipleChoiceQuestion or TrueFalseQuestion, Hibernate's proxy can call any method which is
    // fully resolved in the base class and thus will not initiate loading the actual child class like it normally would
    // if the method/field was defined in the child class. Thus, since the proxy was not initialised, Java will throw an
    // error when you try to cast '(TrueFalseQuestion) question' because the underlying type is not QuestionEntity but
    // QuestionEntity$HibernateProxy...
    // Hibernate's proxies contain metadata and know the underlying type of the entity and will override the instanceof
    // check to return true or false based on the actual class.
    // HOWEVER, because the question passed in here has the declare type QuestionEntity, Hibernate will create proxies
    // of the QuestionEntity class not the actual runtime class, MCQ or TFQ. Therefore, must force the proxy to
    // initialise or else the code below always throws an IllegalArgumentException.
    @Override
    public QuestionDTO mapQuestion(QuestionEntity question, boolean locked) {

        // Force Hibernate to initialise proxy, o/w instanceof returns false for all subclasses since Hibernate is proxying declared superclass
        QuestionEntity actualImplementation = (QuestionEntity) Hibernate.unproxy(question);

        if (actualImplementation instanceof MultipleChoiceQuestionEntity mcq) {
            return genericQuestionMapper.mapToDTO(mcq, locked);
        } else if (actualImplementation instanceof TrueFalseQuestionEntity tfq) {
            return genericQuestionMapper.mapToDTO(tfq, locked);
        } else {
            throw new IllegalArgumentException("Unrecognised question type");
        }
    }

    // DELETE
    public void deleteQuestionById(Long id) {

        if (questionRepository.multipleChoiceExistsById(id)) {
            learningPlatformServicesFacade.deleteChoices(id);
        }
        questionRepository.deleteQuestionById(id);
    }
}
