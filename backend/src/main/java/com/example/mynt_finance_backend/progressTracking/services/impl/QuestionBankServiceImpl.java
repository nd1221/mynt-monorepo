package com.example.mynt_finance_backend.progressTracking.services.impl;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.QuestionEntity;
import com.example.mynt_finance_backend.learningPlatform.repositories.QuestionRepository;
import com.example.mynt_finance_backend.learningPlatform.services.intermediaries.LearningPlatformServicesFacade;
import com.example.mynt_finance_backend.progressTracking.controllers.utils.QuestionBankSearchParam;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.QuestionBankPageDTO;
import com.example.mynt_finance_backend.progressTracking.services.utils.QuestionBankSpecification;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.QuestionProgressAggregateDTO;
import com.example.mynt_finance_backend.progressTracking.domain.entities.CourseProgressTracker;
import com.example.mynt_finance_backend.progressTracking.domain.entities.LessonProgressTracker;
import com.example.mynt_finance_backend.progressTracking.services.QuestionBankService;
import com.example.mynt_finance_backend.progressTracking.services.intermediaries.ProgressTrackingServicesFacade;
import com.example.mynt_finance_backend.util.progressTrackingUtils.QuestionFetchingUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class QuestionBankServiceImpl implements QuestionBankService {

    private final QuestionRepository questionRepository;

    private final ProgressTrackingServicesFacade progressTrackingServicesFacade;

    private final LearningPlatformServicesFacade learningPlatformServicesFacade;

    @Autowired
    public QuestionBankServiceImpl(QuestionRepository questionRepository, @Lazy ProgressTrackingServicesFacade progressTrackingServicesFacade, @Lazy LearningPlatformServicesFacade learningPlatformServicesFacade) {
        this.questionRepository = questionRepository;
        this.progressTrackingServicesFacade = progressTrackingServicesFacade;
        this.learningPlatformServicesFacade = learningPlatformServicesFacade;
    }

    @Override
    public QuestionBankPageDTO getFilteredQuestions(Pageable pageable, QuestionBankSearchParam searchParams, long courseProgressTrackerId) {

        CourseProgressTracker cpt = progressTrackingServicesFacade.findCourseProgressTrackerById(courseProgressTrackerId);

        Specification<QuestionEntity> spec = getSpecification(searchParams, pageable.getSort(), courseProgressTrackerId);
        Pageable queryPageable = getPageable(pageable);
        Page<QuestionEntity> questions = questionRepository.findAll(spec, queryPageable);

        Page<QuestionProgressAggregateDTO> dtoPage = mapToQuestionBankDTOPage(questions, cpt);

        return new QuestionBankPageDTO(
                dtoPage,
                getQuestionBankMeta(searchParams, cpt.getCourse().getId())
        );
    }

    private Specification<QuestionEntity> getSpecification(QuestionBankSearchParam params, Sort sort, long courseProgressTrackerId) {
        return Specification.where(QuestionBankSpecification.applyFilters(params, sort, courseProgressTrackerId));
    }

    private Pageable getPageable(Pageable pageable) {
        return PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize()
        );
    }

    private Page<QuestionProgressAggregateDTO> mapToQuestionBankDTOPage(Page<QuestionEntity> questions, CourseProgressTracker cpt) {
        return questions.map(question -> {
            LessonProgressTracker lpt = getCorrespondingLessonTracker(question, cpt);
            boolean questionLocked = lpt == null || QuestionFetchingUtil.isQuestionLocked(question, lpt);
            return getQuestionBankDTO(question, cpt.getId(), questionLocked);
        });
    }

    private QuestionProgressAggregateDTO getQuestionBankDTO(QuestionEntity question, long courseProgressTrackerId, boolean questionLocked) {
        return new QuestionProgressAggregateDTO(
                learningPlatformServicesFacade.mapQuestionToDTO(question, questionLocked),
                progressTrackingServicesFacade.getQuestionProgressTrackerIfExists(question.getId(), courseProgressTrackerId),
                progressTrackingServicesFacade.getAggregateTrackerByQuestionId(question.getId())
        );
    }

    private LessonProgressTracker getCorrespondingLessonTracker(QuestionEntity question, CourseProgressTracker cpt) {
        for (LessonProgressTracker lpt : cpt.getLessonProgressTrackers()) {
            if (lpt.getLesson().getQuestions().contains(question)) {
                return lpt;
            }
        }
        return null;
    }

    private QuestionBankPageDTO.Meta getQuestionBankMeta(QuestionBankSearchParam params, int courseId) {
        Long numberOfCourseQuestions = learningPlatformServicesFacade.getNumberOfQuestionsInCourse(courseId);
        Long numberOfSectionQuestions = params.section() == 0 ? null : learningPlatformServicesFacade.getNumberOfQuestionsInSection(params.section());
        Long numberOfLessonQuestions = params.lesson() == 0 ? null : learningPlatformServicesFacade.getNumberOfQuestionsInLesson(params.lesson());
        return new QuestionBankPageDTO.Meta(
                numberOfCourseQuestions,
                numberOfSectionQuestions,
                numberOfLessonQuestions
        );
    }
}
