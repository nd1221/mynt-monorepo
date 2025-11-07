package com.example.mynt_finance_backend.progressTracking.services.impl;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.SectionEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TestEntity;
import com.example.mynt_finance_backend.learningPlatform.services.intermediaries.LearningPlatformServicesFacade;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.TestProgressTrackerDTO;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.TestProgressUpdateDTO;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.TestReviewDTO;
import com.example.mynt_finance_backend.progressTracking.domain.entities.CourseProgressTracker;
import com.example.mynt_finance_backend.progressTracking.domain.entities.TestProgressTracker;
import com.example.mynt_finance_backend.progressTracking.domain.entities.TestReview;
import com.example.mynt_finance_backend.progressTracking.repositories.TestProgressTrackerRepository;
import com.example.mynt_finance_backend.progressTracking.repositories.TestReviewRepository;
import com.example.mynt_finance_backend.progressTracking.services.TestProgressTrackerService;
import com.example.mynt_finance_backend.progressTracking.services.intermediaries.ProgressTrackingServicesFacade;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TestProgressTrackerServiceImpl implements TestProgressTrackerService {

    private final TestProgressTrackerRepository testProgressTrackerRepository;

    private final TestReviewRepository testReviewRepository;

    private final Mapper<TestProgressTrackerDTO, TestProgressTracker> progressMapper;

    private final Mapper<TestReviewDTO, TestReview> reviewMapper;

    private final ProgressTrackingServicesFacade progressTrackingServicesFacade;

    private final LearningPlatformServicesFacade learningPlatformServicesFacade;

    @Autowired
    public TestProgressTrackerServiceImpl(TestProgressTrackerRepository testProgressTrackerRepository, TestReviewRepository testReviewRepository, Mapper<TestProgressTrackerDTO, TestProgressTracker> progressMapper, Mapper<TestReviewDTO, TestReview> reviewMapper, @Lazy ProgressTrackingServicesFacade progressTrackingServicesFacade, @Lazy LearningPlatformServicesFacade learningPlatformServicesFacade) {
        this.testProgressTrackerRepository = testProgressTrackerRepository;
        this.testReviewRepository = testReviewRepository;
        this.progressMapper = progressMapper;
        this.reviewMapper = reviewMapper;
        this.progressTrackingServicesFacade = progressTrackingServicesFacade;
        this.learningPlatformServicesFacade = learningPlatformServicesFacade;
    }

    @Override
    public boolean exists(Long id) {
        return testProgressTrackerRepository.existsById(id);
    }

    @Override
    public TestProgressTrackerDTO readByCourseProgressTrackerAndTest(long courseProgressTrackerId, int testId) {
        Optional<TestProgressTracker> tpt = testProgressTrackerRepository.readByCourseProgressTrackerAndTest(courseProgressTrackerId, testId);
        return tpt.map(progressMapper::mapToDTO).orElse(null);
    }

    @Override
    public TestProgressTrackerDTO updateTestProgress(long courseProgressTrackerId, int testId, TestProgressUpdateDTO updateDTO) {
        Optional<TestProgressTracker> tpt = testProgressTrackerRepository.readByCourseProgressTrackerAndTest(courseProgressTrackerId, testId);
        TestProgressTracker updatedTPT = tpt
                .map(testProgressTracker -> updateTracker(testProgressTracker, updateDTO))
                .orElseGet(() -> createTracker(courseProgressTrackerId, testId, updateDTO));
        saveReview(updateDTO, updatedTPT);
        return progressMapper.mapToDTO(updatedTPT);
    }

    @Override
    public List<TestReviewDTO> getReviewsPastN(long testProgressTrackerId, int pastMonths) {
        List<TestReview> foundReviews = testReviewRepository.findAllFromDate(testProgressTrackerId, LocalDate.now().minusMonths(pastMonths));
        return foundReviews.stream()
                .map(reviewMapper::mapToDTO)
                .toList();
    }

    private TestProgressTracker updateTracker(TestProgressTracker tpt, TestProgressUpdateDTO updateDTO) {

        int currentN = tpt.getNumberOfAttempts();
        double resultPercentage = ((double) updateDTO.score() / tpt.getTest().getNumberOfQuestions()) * 100;
        double questionsAttemptedPercentage = ((double) updateDTO.questionsAttempted() / tpt.getTest().getNumberOfQuestions()) * 100;

        // Update TestProgressTracker
        updateFieldIfNotNull(tpt::setAveragePercentage, calculateRollingAverageCumulative(tpt.getAveragePercentage(), resultPercentage, currentN));
        updateFieldIfNotNull(tpt::setAverageTestTimeMillis, calculateRollingAverageCumulative(tpt.getAverageTestTimeMillis(), updateDTO.testTimeMillis(), currentN));
        if (updateDTO.outOfTime()) {
            tpt.incrementOutOfTimeCount();
        }
        updateFieldIfNotNull(tpt::setAveragePercentQuestionsAttempted, calculateRollingAverageCumulative(tpt.getAveragePercentQuestionsAttempted(), questionsAttemptedPercentage, currentN));
        tpt.setLastAttempted(LocalDateTime.now());
        tpt.incrementNumberOfAttempts();

        return tpt;
    }

    private TestProgressTracker createTracker(long courseProgressTrackerId, int testId, TestProgressUpdateDTO updateDTO) {

        // Initialise TestProgressTracker
        TestProgressTracker tpt = new TestProgressTracker();
        tpt.setFirstAttempted(LocalDateTime.now());
        tpt.setLastAttempted(LocalDateTime.now());
        tpt.setOutOfTimeCount(0);
        tpt.setNumberOfAttempts(0);

        CourseProgressTracker cpt = progressTrackingServicesFacade.findCourseProgressTrackerById(courseProgressTrackerId);
        cpt.addTestProgressTracker(tpt);
        tpt.setCourseProgressTracker(cpt);
        TestEntity test = learningPlatformServicesFacade.findTestById(testId);
        tpt.setTest(test);
        SectionEntity section = learningPlatformServicesFacade.findSectionById(updateDTO.sectionId());
        tpt.setSection(section);

        // Propagate update
        double resultPercentage = ((double) updateDTO.score() / test.getNumberOfQuestions()) * 100;
        double percentQuestionsAttempted = ((double) updateDTO.questionsAttempted() / test.getNumberOfQuestions()) * 100;

        updateFieldIfNotNull(tpt::setAveragePercentage, resultPercentage);
        updateFieldIfNotNull(tpt::setAverageTestTimeMillis, updateDTO.testTimeMillis());
        if (updateDTO.outOfTime()) {
            tpt.incrementOutOfTimeCount();
        }
        updateFieldIfNotNull(tpt::setAveragePercentQuestionsAttempted, percentQuestionsAttempted);
        tpt.incrementNumberOfAttempts();

        return testProgressTrackerRepository.save(tpt);
    }

    private void saveReview(TestProgressUpdateDTO updateDTO, TestProgressTracker tpt) {
        TestReview review = new TestReview();
        review.setTestProgressTracker(tpt);
        review.setDateAttempted(LocalDate.now());
        review.setScore(updateDTO.score());
        review.setNumberOfQuestionsAttempted(updateDTO.questionsAttempted());
        review.setOutOfTime(updateDTO.outOfTime());
        review.setTestTimeMillis(updateDTO.testTimeMillis());
        testReviewRepository.save(review);
    }
}