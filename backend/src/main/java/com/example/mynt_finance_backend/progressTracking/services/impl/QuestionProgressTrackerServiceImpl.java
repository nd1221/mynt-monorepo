package com.example.mynt_finance_backend.progressTracking.services.impl;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.QuestionEntity;
import com.example.mynt_finance_backend.learningPlatform.services.intermediaries.LearningPlatformServicesFacade;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.PracticeQuestionDTO;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.QuestionProgressTrackerDTO;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.QuestionProgressUpdateDTO;
import com.example.mynt_finance_backend.progressTracking.domain.entities.AggregateQuestionTracker;
import com.example.mynt_finance_backend.progressTracking.domain.entities.LessonProgressTracker;
import com.example.mynt_finance_backend.progressTracking.domain.entities.QuestionProgressTracker;
import com.example.mynt_finance_backend.progressTracking.repositories.QuestionProgressTrackerRepository;
import com.example.mynt_finance_backend.progressTracking.services.QuestionProgressTrackerService;
import com.example.mynt_finance_backend.progressTracking.services.SpacedRepetitionService;
import com.example.mynt_finance_backend.progressTracking.services.intermediaries.ProgressTrackingServicesFacade;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import com.example.mynt_finance_backend.util.progressTrackingUtils.QuestionFetchingUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class    QuestionProgressTrackerServiceImpl implements QuestionProgressTrackerService {

    private final QuestionProgressTrackerRepository questionProgressTrackerRepository;

    private final Mapper<QuestionProgressTrackerDTO, QuestionProgressTracker> mapper;

    private final SpacedRepetitionService spacedRepetitionService;

    private final ProgressTrackingServicesFacade progressTrackingServicesFacade;

    private final LearningPlatformServicesFacade learningPlatformServicesFacade;

    @Autowired
    public QuestionProgressTrackerServiceImpl(QuestionProgressTrackerRepository questionProgressTrackerRepository, Mapper<QuestionProgressTrackerDTO, QuestionProgressTracker> mapper, SpacedRepetitionService spacedRepetitionService, @Lazy ProgressTrackingServicesFacade progressTrackingServicesFacade, @Lazy LearningPlatformServicesFacade learningPlatformServicesFacade) {
        this.questionProgressTrackerRepository = questionProgressTrackerRepository;
        this.mapper = mapper;
        this.spacedRepetitionService = spacedRepetitionService;
        this.progressTrackingServicesFacade = progressTrackingServicesFacade;
        this.learningPlatformServicesFacade = learningPlatformServicesFacade;
    }

    @Override
    public boolean exists(Long id) {
        return questionProgressTrackerRepository.existsById(id);
    }

    @Override
    public void createQuestionProgressTracker(long courseTrackerId, int lessonId, long questionId, long aggregateTrackerId) {

        // Check if already exists
        long matchingCount = questionProgressTrackerRepository.existsByCourseTrackerAndLessonAndQuestion(courseTrackerId, lessonId, questionId);
        if (matchingCount > 0) {
            return;
        }

        LessonProgressTracker lpt = progressTrackingServicesFacade.findLessonProgressTrackerByCourseTrackerAndLesson(courseTrackerId, lessonId);
        AggregateQuestionTracker aqt = progressTrackingServicesFacade.findAggregateQuestionTrackerById(aggregateTrackerId);
        if (lpt == null) {
            return;
        }

        QuestionEntity question = learningPlatformServicesFacade.findQuestionById(questionId);
        QuestionProgressTracker qpt = createTracker(lpt, question, aqt);

        lpt.addQuestionProgressTracker(qpt);

        // Check if question is core content and, if so, update lesson completeness criterion
        progressTrackingServicesFacade.updateCompleteness(lpt, question.isCore());

        questionProgressTrackerRepository.save(qpt);
    }

    @Override
    public QuestionProgressTrackerDTO getByCourseTrackerAndLessonAndQuestion(long courseTrackerId, int lessonId, long questionId) {
        return mapper.mapToDTO(questionProgressTrackerRepository.getByCourseTrackerAndLessonAndQuestion(courseTrackerId, lessonId, questionId));
    }

    @Override
    public QuestionProgressTrackerDTO readById(long questionProgressTrackerId) {
        return mapper.mapToDTO(
                questionProgressTrackerRepository.findById(questionProgressTrackerId).get()
        );
    }

    @Override
    public QuestionProgressTracker readEntityById(long questionProgressTrackerId) {
        return questionProgressTrackerRepository.findById(questionProgressTrackerId).get();
    }

    @Override
    public QuestionProgressTrackerDTO readByCourseTrackerAndQuestionIdIfExists(long questionId, long courseProgressTrackerId) {
        Optional<QuestionProgressTracker> qpt = questionProgressTrackerRepository.findByCourseTrackerAndQuestionId(questionId, courseProgressTrackerId);
        return qpt.map(mapper::mapToDTO).orElse(null);
    }

    @Override
    public QuestionProgressTrackerDTO updateQuestionProgress(long questionProgressTrackerId, QuestionProgressUpdateDTO updateDTO) {

        if (!updateDTO.answered()) {
            return null;
        }

        QuestionProgressTracker qpt = questionProgressTrackerRepository.findById(questionProgressTrackerId).get();

        updateAverageQuestionTime(qpt, updateDTO.questionTime());
        updateCounts(qpt, updateDTO.wasCorrect());
        updateAccuracy(qpt);
        updateDates(qpt);

        // Update lesson and course progress trackers
        progressTrackingServicesFacade.updateLessonProgressTracker(
                qpt.getLessonProgressTracker(),
                qpt.getQuestion().getId(),
                updateDTO.wasCorrect(),
                updateDTO.questionTime()
        );

        return mapper.mapToDTO(qpt);
    }

    @Override
    public List<PracticeQuestionDTO> getDueQuestions(long lessonProgressTrackerId, int numberRequested) {
        Pageable pageLimit = PageRequest.of(0, numberRequested);
        List<QuestionProgressTracker> qpts = questionProgressTrackerRepository.getQuestionsDueToday(lessonProgressTrackerId, pageLimit);
        return getPracticeQuestionDTOs(qpts);
    }

    @Override
    public List<PracticeQuestionDTO> getQuestions(long lessonProgressTrackerId) {
        List<QuestionProgressTracker> qpts = questionProgressTrackerRepository.getNextReviewQuestions(lessonProgressTrackerId);
        return getPracticeQuestionDTOs(qpts);
    }

    @Override
    public List<PracticeQuestionDTO> getQuestions(long lessonProgressTrackerId, int numberRequested) {
        Pageable pageLimit = PageRequest.of(0, numberRequested);
        List<QuestionProgressTracker> qpts = questionProgressTrackerRepository.getNextReviewQuestions(lessonProgressTrackerId, pageLimit);
        return getPracticeQuestionDTOs(qpts);
    }

    @Override
    public void updateUserRating(long questionProgressTrackerId, int newRating) {
        QuestionProgressTracker qpt = questionProgressTrackerRepository.findById(questionProgressTrackerId).get();
        updateSpacedRepetition(qpt, newRating);
    }

    private QuestionProgressTracker createTracker(LessonProgressTracker lpt, QuestionEntity question, AggregateQuestionTracker aqt) {

        QuestionProgressTracker qpt = new QuestionProgressTracker();
        qpt.setQuestion(question);
        qpt.setTotalCount(0);
        qpt.setCorrectCount(0);
        qpt.setCorrectStreak(0);
        qpt.setFirstReviewedDate(null);
        qpt.setLastReviewedDate(null);
        qpt.setNextReviewDate(null);
        qpt.setAverageQuestionTime(Duration.ofMillis(0));
        qpt.setRepetitions(0);
        qpt.setEasinessFactor(0);
        qpt.setInterval(0);
        qpt.setUserRating(0);
        qpt.setLessonProgressTracker(lpt);
        qpt.setAggregateQuestionTracker(aqt);

        return qpt;
    }

    private void updateAverageQuestionTime(QuestionProgressTracker qpt, long newTime) {
        qpt.setAverageQuestionTime(Duration.ofMillis(
                calculateRollingAverageCumulative( //default method from ProgressService
                        qpt.getAverageQuestionTime().toMillis(),
                        newTime,
                        qpt.getTotalCount()
                )
        ));
    }

    private void updateCounts(QuestionProgressTracker qpt, boolean wasCorrect) {
        qpt.incrementCount();
        if (wasCorrect) {
            qpt.incrementCorrectCount();
            qpt.incrementStreak();
        } else {
            qpt.resetStreak();
        }
    }

    private void updateAccuracy(QuestionProgressTracker qpt) {
        qpt.setAccuracy(
                (double) qpt.getCorrectCount() / qpt.getTotalCount() * 100
        );
    }

    private void updateDates(QuestionProgressTracker qpt) {
        LocalDate today = LocalDate.now();
        boolean wasReviewedToday = qpt.getLastReviewedDate() != null && qpt.getLastReviewedDate().equals(today);
        if (!wasReviewedToday) {
            qpt.setLastReviewedDate(today);
            if (qpt.getFirstReviewedDate() == null) {
                qpt.setFirstReviewedDate(today);
            }
        }
    }

    private void updateSpacedRepetition(QuestionProgressTracker qpt, int newUserRating) {

        if (newUserRating < 0 || newUserRating > 5) {
            throw new IllegalArgumentException("Spaced repetition userRating should be bounded between 0-5.");
        }

        qpt.setUserRating(newUserRating);
        spacedRepetitionService.runAlgorithm(qpt);
        qpt.setNextReviewDate(LocalDate.now().plusDays(qpt.getInterval()));
    }

    private List<PracticeQuestionDTO> getPracticeQuestionDTOs(List<QuestionProgressTracker> qpts) {
        return qpts.stream().map(
                qpt -> new PracticeQuestionDTO(
                        learningPlatformServicesFacade.mapQuestionToDTO(
                                qpt.getQuestion(),
                                QuestionFetchingUtil.isQuestionLocked(qpt.getQuestion(), qpt.getLessonProgressTracker())
                        ),
                        qpt.getId()
                )
        )
        .toList();
    }

    @Override
    public Long findTotalCourseQuestionsAnsweredByCourse(int courseId, long courseTrackerId) {
        return questionProgressTrackerRepository.findTotalCourseQuestionsAnsweredByCourse(courseTrackerId, courseId);
    }

    @Override
    public Long findTotalCourseQuestionsAnsweredByCourseTrackerAndDifficulty(int courseId, long courseTrackerId, double lowerThreshold, double upperThreshold) {
        return questionProgressTrackerRepository.findTotalCourseQuestionsAnsweredByCourseTrackerAndDifficulty(courseTrackerId, courseId, lowerThreshold, upperThreshold);
    }

    @Override
    public List<QuestionProgressTracker> findAllBySpecification(Specification<QuestionProgressTracker> spec) {
        return questionProgressTrackerRepository.findAll(spec);
    }

    @Override
    public Page<QuestionProgressTracker> findPageBySpecification(Specification<QuestionProgressTracker> spec, Pageable pageable) {
        return questionProgressTrackerRepository.findAll(spec, pageable);
    }

    @Override
    public QuestionProgressTrackerDTO mapToDTO(QuestionProgressTracker qpt) {
        return mapper.mapToDTO(qpt);
    }
}
