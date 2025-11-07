package com.example.mynt_finance_backend.progressTracking.services.impl;

import com.example.mynt_finance_backend.progressTracking.domain.dtos.AggregateReviewDTO;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.QuestionReviewDTO;
import com.example.mynt_finance_backend.progressTracking.domain.entities.QuestionReview;
import com.example.mynt_finance_backend.progressTracking.repositories.QuestionReviewRepository;
import com.example.mynt_finance_backend.progressTracking.services.QuestionReviewService;
import com.example.mynt_finance_backend.progressTracking.services.intermediaries.ProgressTrackingServicesFacade;
import com.example.mynt_finance_backend.progressTracking.services.utils.SpecificationUtil;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import com.example.mynt_finance_backend.util.progressTrackingUtils.Difficulty;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.mynt_finance_backend.util.progressTrackingUtils.Difficulty.*;

@Service
@Transactional
public class QuestionReviewServiceImpl implements QuestionReviewService {

    private final QuestionReviewRepository questionReviewRepository;

    private final Mapper<QuestionReviewDTO, QuestionReview> mapper;

    private final ProgressTrackingServicesFacade progressTrackingServicesFacade;

    @Autowired
    public QuestionReviewServiceImpl(QuestionReviewRepository questionReviewRepository, Mapper<QuestionReviewDTO, QuestionReview> mapper, @Lazy ProgressTrackingServicesFacade progressTrackingServicesFacade) {
        this.questionReviewRepository = questionReviewRepository;
        this.mapper = mapper;
        this.progressTrackingServicesFacade = progressTrackingServicesFacade;
    }

    @Override
    public boolean exists(Long id) {
        return questionReviewRepository.existsById(id);
    }

    @Override
    public List<QuestionReviewDTO> getReviewsPastYear(long questionProgressTrackerId, int months) {
        List<QuestionReview> reviews = questionReviewRepository.findAllFromDate(questionProgressTrackerId, LocalDate.now().minusMonths(months));
        return reviews.stream()
                .map(mapper::mapToDTO)
                .toList();
    }

    @Override
    public List<QuestionReviewDTO> getCourseReviews(long courseProgressTrackerId) {
        List<QuestionReview> reviews = questionReviewRepository.findAllByCourseProgressTracker(courseProgressTrackerId);
        return reviews.stream()
                .map(mapper::mapToDTO)
                .toList();
    }

    @Override
    public List<QuestionReviewDTO> getLessonReviews(long lessonProgressTrackerId) {
        List<QuestionReview> reviews = questionReviewRepository.findAllByLessonProgressTracker(lessonProgressTrackerId);
        return reviews.stream()
                .map(mapper::mapToDTO)
                .toList();
    }

    @Override
    public List<QuestionReviewDTO> getLessonReviewsWithParams(long lessonProgressTrackerId, int months, int difficulty) {

        List<QuestionReview> reviews = questionReviewRepository.findAllFromDateByLessonTrackerByDifficulty(
                lessonProgressTrackerId,
                LocalDate.now().minusMonths(months),
                difficulty == 0 ? HARD.getThreshold() : SpecificationUtil.difficultyLowerBound(difficulty),
                difficulty == 0 ? UPPER_BOUND.getThreshold() : SpecificationUtil.difficultyUpperBound(difficulty)
        );
        return reviews.stream()
                .map(mapper::mapToDTO)
                .toList();
    }

    @Override
    public void createReview(long questionProgressTrackerId, long userProgressTrackerId, QuestionReviewDTO dto) {

        QuestionReview newReview = new QuestionReview();
        newReview.setReviewDate(LocalDate.now());
        newReview.setQuestionTime(Duration.ofMillis(dto.questionTimeMillis()));
        newReview.setCorrect(dto.correct());
        newReview.setUserRating(dto.userRating());

        progressTrackingServicesFacade.setReviewQPT(questionProgressTrackerId, newReview);
        progressTrackingServicesFacade.setReviewUPT(userProgressTrackerId, newReview);
        questionReviewRepository.save(newReview);
    }

    @Override
    public List<AggregateReviewDTO> getRecentActivityAggregateReviews(long userProgressTrackerId) {
        // Returns aggregate review metadata for the past 30 days

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(29);

        List<Object[]> aggregateReviews = questionReviewRepository.getAggregateQuestionReviewsInDateRange(userProgressTrackerId, startDate, endDate);
        List<LocalDate> dateArray = generateDateArray(endDate);
        Map<LocalDate, Long> aggregateReviewMap = generateAggregateReviewMap(aggregateReviews);

        return dateArray.stream()
                .map(date -> {
                    if (aggregateReviewMap.containsKey(date)) {
                        return new AggregateReviewDTO(
                                date.toString(),
                                aggregateReviewMap.get(date).intValue()
                        );
                    } else {
                        return new AggregateReviewDTO(date.toString(), 0);
                    }
                })
                .toList();
    }

    private List<LocalDate> generateDateArray(LocalDate endDate) {
        List<LocalDate> dateArray = new ArrayList<>();
        dateArray.add(endDate);
        for (int i = 1; i < 30; i++) {
            dateArray.add(endDate.minusDays(i));
        }
        return dateArray;
    }

    private Map<LocalDate, Long> generateAggregateReviewMap(List<Object[]> aggregateReviews) {
         return aggregateReviews.stream()
                .collect(Collectors.toMap(
                        review -> (LocalDate) review[0],
                        review -> (Long) review[1]
                ));
    }
}
