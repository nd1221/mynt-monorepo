package com.example.mynt_finance_backend.progressTracking.services.impl;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.QuestionEntity;
import com.example.mynt_finance_backend.learningPlatform.services.QuestionService;
import com.example.mynt_finance_backend.progressTracking.controllers.utils.CustomSessionSearchParam;
import com.example.mynt_finance_backend.progressTracking.controllers.utils.ReviewSessionSearchParam;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.QuestionProgressAggregateDTO;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.ReviewSessionDTO;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.ReviewSessionMetadataDTO;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.ReviewSessionRequestDTO;
import com.example.mynt_finance_backend.progressTracking.domain.entities.CourseProgressTracker;
import com.example.mynt_finance_backend.progressTracking.domain.entities.QuestionProgressTracker;
import com.example.mynt_finance_backend.progressTracking.services.AggregateQuestionTrackerService;
import com.example.mynt_finance_backend.progressTracking.services.CourseProgressTrackerService;
import com.example.mynt_finance_backend.progressTracking.services.QuestionProgressTrackerService;
import com.example.mynt_finance_backend.progressTracking.services.ReviewSessionService;
import com.example.mynt_finance_backend.progressTracking.services.utils.CustomSessionSpecification;
import com.example.mynt_finance_backend.progressTracking.services.utils.ReviewSessionSpecification;
import com.example.mynt_finance_backend.progressTracking.services.utils.UnseenQuestionSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ReviewSessionServiceImpl implements ReviewSessionService {

    private final int NUMBER_REQUESTED_FALLBACK = 12;

    private final CourseProgressTrackerService courseProgressTrackerService;

    private final QuestionService questionService;

    private final QuestionProgressTrackerService questionProgressTrackerService;

    private final AggregateQuestionTrackerService aggregateQuestionTrackerService;

    @Autowired
    public ReviewSessionServiceImpl(CourseProgressTrackerService courseProgressTrackerService, QuestionService questionService, QuestionProgressTrackerService questionProgressTrackerService, AggregateQuestionTrackerService aggregateQuestionTrackerService) {
        this.courseProgressTrackerService = courseProgressTrackerService;
        this.questionService = questionService;
        this.questionProgressTrackerService = questionProgressTrackerService;
        this.aggregateQuestionTrackerService = aggregateQuestionTrackerService;
    }

    @Override
    public ReviewSessionMetadataDTO loadReviewSession(long courseProgressTrackerId, ReviewSessionSearchParam params) {
        List<QuestionProgressTracker> qpts = getQuestionProgressTrackers(courseProgressTrackerId, params);
        List<QuestionEntity> unseenQuestions = params.includeUnseen() || params.slider() == 0 ? getUnseenQuestions(courseProgressTrackerId, params) : new ArrayList<>();
        return mapToDTO(qpts, unseenQuestions);
    }

    @Override
    public ReviewSessionMetadataDTO loadCustomSession(long courseProgressTrackerId, CustomSessionSearchParam params) {
        List<QuestionProgressTracker> qpts = getQuestionProgressTrackers(courseProgressTrackerId, params);
        List<QuestionEntity> unseenQuestions = params.slider() != 0 ? getUnseenQuestions(courseProgressTrackerId, params) : new ArrayList<>();
        return mapToDTO(qpts, unseenQuestions);
    }

    private ReviewSessionMetadataDTO mapToDTO(List<QuestionProgressTracker> qpts, List<QuestionEntity> unseenQuestions) {
        return new ReviewSessionMetadataDTO(
                qpts.stream()
                        .map(QuestionProgressTracker::getId)
                        .toList(),
                unseenQuestions.stream()
                        .map(QuestionEntity::getId)
                        .toList(),
                getNumberOfCoreQuestions(qpts, unseenQuestions)
        );
    }

    private Specification<QuestionProgressTracker> getReviewSessionSpecification(long courseProgressTrackerId, ReviewSessionSearchParam params) {
        return Specification.where(ReviewSessionSpecification.applyReviewSessionFilters(params, courseProgressTrackerId));
    }

    private Specification<QuestionProgressTracker> getCustomSessionSpecification(long courseProgressTrackerId, CustomSessionSearchParam params) {
        return Specification.where(CustomSessionSpecification.applyCustomSessionFilters(params, courseProgressTrackerId));
    }

    private Pageable getReviewPageable(int numberRequested) {
        // Only ever have to return the first page as the filters will be updated dynamically
        // such that user will always request the foremost items at any given time.
        return PageRequest.of(
                0,
                numberRequested
        );
    }

    private List<QuestionProgressTracker> getQuestionProgressTrackers(long courseProgressTrackerId, ReviewSessionSearchParam params) {
        Specification<QuestionProgressTracker> spec = getReviewSessionSpecification(courseProgressTrackerId, params);
        if (params.numberRequested() == 0) { // Confusingly, this means get all
            return questionProgressTrackerService.findAllBySpecification(spec);
        } else {
            Pageable queryPageable = getReviewPageable(params.numberRequested());
            return questionProgressTrackerService.findPageBySpecification(spec, queryPageable).toList();
        }
    }

    private List<QuestionProgressTracker> getQuestionProgressTrackers(long courseProgressTrackerId, CustomSessionSearchParam params) {
        Specification<QuestionProgressTracker> spec = getCustomSessionSpecification(courseProgressTrackerId, params);
        if (params.numberRequested() == 0) { // Confusingly, this means get all
            return questionProgressTrackerService.findAllBySpecification(spec);
        } else {
            Pageable queryPageable = getReviewPageable(params.numberRequested());
            return questionProgressTrackerService.findPageBySpecification(spec, queryPageable).toList();
        }
    }

    private Specification<QuestionEntity> getUnseenQuestionSpecification(long courseProgressTrackerId, ReviewSessionSearchParam params) {
        boolean complete = courseProgressTrackerService.isComplete(courseProgressTrackerId);
        return Specification.where(UnseenQuestionSpecification.applyUnseenQuestionFilters(params, courseProgressTrackerId, complete));
    }

    private Specification<QuestionEntity> getUnseenQuestionSpecification(long courseProgressTrackerId, CustomSessionSearchParam params) {
        boolean complete = courseProgressTrackerService.isComplete(courseProgressTrackerId);
        return Specification.where(UnseenQuestionSpecification.applyCustomUnseenQuestionFilters(params, courseProgressTrackerId, complete));
    }

    private Pageable getUnseenQuestionsPageable(int numberRequested, double slider) {
        // Only ever have to return the first page as the filters will be updated dynamically
        // such that user will always request the foremost items at any given time.

        // In review setup, proportion of unseen questions that can be chosen is [0.1, 0.2, 0.3, 0.4, 0.5]

        int numberOfUnseenRequested = numberRequested == 0 ? NUMBER_REQUESTED_FALLBACK : (int) (numberRequested * slider);
        return PageRequest.of(
                0,
                numberOfUnseenRequested
        );
    }

    private List<QuestionEntity> getUnseenQuestions(long courseProgressTrackerId, ReviewSessionSearchParam params) {
        Specification<QuestionEntity> spec = getUnseenQuestionSpecification(courseProgressTrackerId, params);
        Pageable queryPageable = getUnseenQuestionsPageable(params.numberRequested(), params.slider());
        return questionService.findAllBySpecification(spec, queryPageable).toList();
    }

    private List<QuestionEntity> getUnseenQuestions(long courseProgressTrackerId, CustomSessionSearchParam params) {
        Specification<QuestionEntity> spec = getUnseenQuestionSpecification(courseProgressTrackerId, params);
        Pageable queryPageable = getUnseenQuestionsPageable(params.numberRequested(), params.slider());
        return questionService.findAllBySpecification(spec, queryPageable).toList();
    }

    private int getNumberOfCoreQuestions(List<QuestionProgressTracker> qpts, List<QuestionEntity> unseenQuestions) {

        int core = 0;

        for (QuestionProgressTracker qpt : qpts) {
            if (qpt.getQuestion().isCore()) {
                core++;
            }
        }
        for (QuestionEntity question : unseenQuestions) {
            if (question.isCore()) {
                core++;
            }
        }

        return core;
    }

    @Override
    public List<QuestionProgressAggregateDTO> getSession(ReviewSessionRequestDTO sessionRequest) {

        List<QuestionProgressAggregateDTO> seenQuestions = sessionRequest.questionTrackerIds().stream()
                .map(questionProgressTrackerService::readEntityById)
                .filter(Objects::nonNull)
                .map(qpt -> new QuestionProgressAggregateDTO(
                        questionService.mapQuestion(qpt.getQuestion(), false),
                        questionProgressTrackerService.mapToDTO(qpt),
                        aggregateQuestionTrackerService.mapToDTO(qpt.getAggregateQuestionTracker())
                ))
                .toList();

        List<QuestionProgressAggregateDTO> unseenQuestions = sessionRequest.unseenQuestionIds().stream()
                .map(questionService::findQuestionById)
                .filter(Objects::nonNull)
                .map(question -> new QuestionProgressAggregateDTO(
                        questionService.mapQuestion(question, false),
                        null,
                        aggregateQuestionTrackerService.getAggregateTrackerByQuestionId(question.getId())
                ))
                .toList();

        ArrayList<QuestionProgressAggregateDTO> dtos = new ArrayList<>();
        dtos.addAll(seenQuestions);
        dtos.addAll(unseenQuestions);
        return dtos;
    }
}
