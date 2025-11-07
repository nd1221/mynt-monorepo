package com.example.mynt_finance_backend.progressTracking.services.impl;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.LessonDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.LessonEntity;
import com.example.mynt_finance_backend.learningPlatform.services.intermediaries.LearningPlatformServicesFacade;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.*;
import com.example.mynt_finance_backend.progressTracking.domain.entities.CourseProgressTracker;
import com.example.mynt_finance_backend.progressTracking.domain.entities.LessonProgressTracker;
import com.example.mynt_finance_backend.progressTracking.repositories.LessonProgressTrackerRepository;
import com.example.mynt_finance_backend.progressTracking.services.LessonProgressTrackerService;
import com.example.mynt_finance_backend.progressTracking.services.intermediaries.ProgressTrackingServicesFacade;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import com.example.mynt_finance_backend.util.commonDTOs.EntityTrackerDTO;
import com.example.mynt_finance_backend.util.progressTrackingUtils.MasteryAndCompletenessUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class LessonProgressTrackerServiceImpl implements LessonProgressTrackerService {

    private final int PRACTICE_QUESTIONS_PER_REQUEST = 5;

    private final double MASTERY_DECAY_PARAM = 0.90;

    private final LessonProgressTrackerRepository lessonProgressTrackerRepository;

    private final Mapper<LessonProgressTrackerDTO, LessonProgressTracker> mapper;

    private final ProgressTrackingServicesFacade progressTrackingServicesFacade;

    private final LearningPlatformServicesFacade learningPlatformServicesFacade;



    @Autowired
    public LessonProgressTrackerServiceImpl(LessonProgressTrackerRepository lessonProgressTrackerRepository, Mapper<LessonProgressTrackerDTO, LessonProgressTracker> mapper, @Lazy ProgressTrackingServicesFacade progressTrackingServicesFacade, @Lazy LearningPlatformServicesFacade learningPlatformServicesFacade) {
        this.lessonProgressTrackerRepository = lessonProgressTrackerRepository;
        this.mapper = mapper;
        this.progressTrackingServicesFacade = progressTrackingServicesFacade;
        this.learningPlatformServicesFacade = learningPlatformServicesFacade;
    }

    @Override
    public boolean exists(Long id) {
        return lessonProgressTrackerRepository.existsById(id);
    }

    @Override
    public LessonProgressTrackerDTO createLessonProgressTracker(Long courseProgressTrackerId, Integer lessonId) {

        if (existsByLessonAndCourseProgressTracker(lessonId, courseProgressTrackerId)) {
            return mapper.mapToDTO(lessonProgressTrackerRepository.findByLessonAndCourseProgressTracker(lessonId, courseProgressTrackerId));
        }

        LessonEntity lesson = learningPlatformServicesFacade.findLessonById(lessonId);
        CourseProgressTracker cpt = progressTrackingServicesFacade.findCourseProgressTrackerById(courseProgressTrackerId);

        LessonProgressTracker lpt = createLessonProgressTracker(lesson, cpt);
        cpt.addLessonProgressTracker(lpt);

        return mapper.mapToDTO(lessonProgressTrackerRepository.save(lpt));
    }

    @Override
    public LessonProgressTrackerDTO readById(Long lessonProgressTrackerId) {
        return mapper.mapToDTO(
                lessonProgressTrackerRepository.findById(lessonProgressTrackerId).get()
        );
    }

    @Override
    public LessonProgressTracker findLessonProgressTrackerById(long lptId) {
        return lessonProgressTrackerRepository.findById(lptId).get();
    }

    @Override
    public void updateLessonProgressTracker(LessonProgressTracker lpt, long questionId, boolean questionCorrect, long questionTime) {

        lpt.setAverageQuestionTime(Duration.ofMillis(
                calculateRollingAverageCumulative(
                        lpt.getAverageQuestionTime().toMillis(),
                        questionTime,
                        lpt.getTotalQuestionsReviewed()
                )
        ));
        lpt.setAccuracy(
                calculateRollingAverageBoolean(
                        lpt.getAccuracy(),
                        lpt.getTotalQuestionsReviewed(),
                        questionCorrect
                )
        );
        lpt.incrementTotalQuestionsReviews();

        LocalDate today = LocalDate.now();
        lpt.setLastPracticeDate(today);
        if (lpt.getFirstPracticeDate() == null) {
            lpt.setFirstPracticeDate(today);
        }

        lpt.setQuestionsDueToday(lessonProgressTrackerRepository.getQuestionsDueToday(lpt.getId()));

        MasteryAndCompletenessUtil.updateMastery(lpt, questionCorrect);

        // Update course progress tracker
        progressTrackingServicesFacade.updateCourseProgressTracker(lpt.getCourseProgressTracker(), questionCorrect, questionTime);
    }

    @Override
    public List<PracticeQuestionDTO> getDueQuestions(long lessonProgressTrackerId) {
        return progressTrackingServicesFacade.getDueQuestions(lessonProgressTrackerId, PRACTICE_QUESTIONS_PER_REQUEST);
    }

    @Override
    public long getNumberOfQuestionsDueToday(long lptId) {
        return lessonProgressTrackerRepository.getNumberOfQuestionsDueToday(lptId, LocalDate.now());
    }

    @Override
    public List<PracticeQuestionDTO> getQuestions(long lessonProgressTrackerId) {
        return progressTrackingServicesFacade.getQuestions(lessonProgressTrackerId);
    }

    @Override
    public List<PracticeQuestionDTO> getQuestions(long lessonProgressTrackerId, int numberRequested) {
        return progressTrackingServicesFacade.getQuestions(lessonProgressTrackerId, numberRequested);
    }

    @Override
    public void updateCompleteness(LessonProgressTracker lpt, boolean isCore) {
        if (isCore) {
            int numberOfCoreQuestions = learningPlatformServicesFacade.getNumberOfCoreQuestionsInLesson(lpt.getLesson().getId());
            double updatedCompleteness = MasteryAndCompletenessUtil.calculateNewCompleteness(numberOfCoreQuestions, lpt.getCompleteness());
            lpt.setCompleteness(updatedCompleteness);
        }
    }

    @Override
    public NavigableEntityTrackerDTO<LessonDTO, LessonProgressTrackerDTO> getLessonOverviewData(long lessonTrackerId) {

        LessonProgressTracker lpt = lessonProgressTrackerRepository.findById(lessonTrackerId).get();
        // Getting lessonDTO through facade to avoid cross-module dependency even though Lesson is directly accessible through LessonProgressTracker
        LessonDTO lessonDTO = learningPlatformServicesFacade.getLessonDTOById(lpt.getLesson().getId());

        List<Long> orderedLptIds = lpt.getCourseProgressTracker().getLessonProgressTrackers().stream()
                .sorted(Comparator.comparing(tracker -> tracker.getLesson().getPosition()))
                .map(LessonProgressTracker::getId)
                .toList();

        int lptIndex = orderedLptIds.indexOf(lpt.getId());
        Long prevId = lptIndex == 0 ? null : orderedLptIds.get(lptIndex - 1);
        Long nextId = lptIndex == orderedLptIds.size() - 1 ? null : orderedLptIds.get(lptIndex + 1);

        return new NavigableEntityTrackerDTO<>(
                new EntityTrackerDTO<>(lessonDTO, mapper.mapToDTO(lpt)),
                prevId,
                nextId
        );
    }

    private LessonProgressTracker createLessonProgressTracker(LessonEntity lesson, CourseProgressTracker cpt) {
        LessonProgressTracker lpt = new LessonProgressTracker();
        lpt.setLesson(lesson);
        lpt.setQuestionProgressTrackers(new HashSet<>());
        lpt.setTotalQuestionsReviewed(0);
        lpt.setAverageQuestionTime(Duration.ofMillis(0));
        lpt.setAccuracy(0);
        lpt.setQuestionsDueToday(0);
        lpt.setFirstPracticeDate(null);
        lpt.setLastPracticeDate(null);
        lpt.setCompleteness(0);
        lpt.setMastery(0);
        lpt.setCourseProgressTracker(cpt);
        return lpt;
    }

    private boolean existsByLessonAndCourseProgressTracker(int lessonId, long courseProgressTrackerId) {
        long matchingLPTCount = lessonProgressTrackerRepository.existsByLessonAndCourseProgressTracker(lessonId, courseProgressTrackerId);
        return matchingLPTCount > 0;
    }
}
