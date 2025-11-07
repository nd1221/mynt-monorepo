package com.example.mynt_finance_backend.progressTracking.services.impl;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.QuestionEntity;
import com.example.mynt_finance_backend.learningPlatform.services.intermediaries.LearningPlatformServicesFacade;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.AggregateQuestionTrackerDTO;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.AggregateQuestionUpdateDTO;
import com.example.mynt_finance_backend.progressTracking.domain.entities.AggregateQuestionTracker;
import com.example.mynt_finance_backend.progressTracking.repositories.AggregateQuestionTrackerRepository;
import com.example.mynt_finance_backend.progressTracking.services.AggregateQuestionTrackerService;
import com.example.mynt_finance_backend.progressTracking.services.intermediaries.ProgressTrackingServicesFacade;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@Transactional
public class AggregateQuestionTrackerServiceImpl implements AggregateQuestionTrackerService {

    private final AggregateQuestionTrackerRepository aggregateQuestionTrackerRepository;

    private final Mapper<AggregateQuestionTrackerDTO, AggregateQuestionTracker> mapper;

    private final ProgressTrackingServicesFacade progressTrackingServicesFacade;

    private final LearningPlatformServicesFacade learningPlatformServicesFacade;

    @Autowired
    public AggregateQuestionTrackerServiceImpl(AggregateQuestionTrackerRepository aggregateQuestionTrackerRepository, Mapper<AggregateQuestionTrackerDTO, AggregateQuestionTracker> mapper, @Lazy ProgressTrackingServicesFacade progressTrackingServicesFacade, @Lazy LearningPlatformServicesFacade learningPlatformServicesFacade) {
        this.aggregateQuestionTrackerRepository = aggregateQuestionTrackerRepository;
        this.mapper = mapper;
        this.progressTrackingServicesFacade = progressTrackingServicesFacade;
        this.learningPlatformServicesFacade = learningPlatformServicesFacade;
    }

    @Override
    public boolean exists(Long id) {
        return aggregateQuestionTrackerRepository.existsById(id);
    }

    @Override
    public void createAggregateQuestionTracker(long questionId) {

        // Only create if aggregate tracker no unique aggregate tracker exists
        long matchingCount = aggregateQuestionTrackerRepository.existsByQuestionId(questionId);
        if (matchingCount > 0) {
            return;
        }

        QuestionEntity question = learningPlatformServicesFacade.findQuestionById(questionId);
        AggregateQuestionTracker aqt = AggregateQuestionTracker.initialise(question);
        aggregateQuestionTrackerRepository.save(aqt);
    }

    @Override
    public AggregateQuestionTrackerDTO updateAggregateQuestionTracker(long aggregateTrackerId, AggregateQuestionUpdateDTO updateDTO) {

        // AggregateQuestionTracker will always exist at this stage, explicit check in calling function
        AggregateQuestionTracker aqt = aggregateQuestionTrackerRepository.findById(aggregateTrackerId).get();
        int currentN = aqt.getNumberOfAttempts();
        double currentAccuracy = aqt.getAccuracy();
        long currentTime = aqt.getAverageQuestionTime().toMillis();
        aqt.setAccuracy(
                calculateRollingAverageBoolean(currentAccuracy, currentN, updateDTO.correct())
        );
        aqt.setAverageQuestionTime(
                Duration.ofMillis(calculateRollingAverageCumulative(currentTime, updateDTO.questionTimeMillis(), currentN))
        );
        aqt.incrementNumberOfAttempts();

        return mapper.mapToDTO(aggregateQuestionTrackerRepository.save(aqt));
    }

    @Override
    public AggregateQuestionTrackerDTO getAggregateTrackerByQuestionId(long questionId) {
        Optional<AggregateQuestionTracker> aqt = aggregateQuestionTrackerRepository.findByQuestionId(questionId);
        return aqt.map(mapper::mapToDTO).orElse(null);
    }

    @Override
    public Long findTotalCourseQuestionsByDifficulty(int courseId, double lowerThreshold, double upperThreshold) {
        return aggregateQuestionTrackerRepository.findNumberOfQuestionsInCourseByDifficulty(courseId, lowerThreshold, upperThreshold);
    }

    @Override
    public AggregateQuestionTrackerDTO mapToDTO(AggregateQuestionTracker aqt) {
        return mapper.mapToDTO(aqt);
    }

    @Override
    public AggregateQuestionTracker findAggregateQuestionTrackerById(long id) {
        return aggregateQuestionTrackerRepository.findById(id).get();
    }
}
