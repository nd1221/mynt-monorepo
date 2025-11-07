package com.example.mynt_finance_backend.progressTracking.services;

import com.example.mynt_finance_backend.progressTracking.domain.dtos.AggregateReviewDTO;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.QuestionReviewDTO;
import com.example.mynt_finance_backend.util.baseInterfaces.Service;

import java.util.List;

public interface QuestionReviewService extends Service<Long> {

    List<QuestionReviewDTO> getReviewsPastYear(long questionProgressTrackerId, int months);

    List<QuestionReviewDTO> getCourseReviews(long courseProgressTrackerId);

    List<QuestionReviewDTO> getLessonReviews(long lessonProgressTrackerId);

    List<QuestionReviewDTO> getLessonReviewsWithParams(long lessonProgressTrackerId, int months, int difficulty);

    void createReview(long questionProgressTrackerId, long userProgressTrackerId, QuestionReviewDTO dto);

    List<AggregateReviewDTO> getRecentActivityAggregateReviews(long userProgressTrackerId);
}
