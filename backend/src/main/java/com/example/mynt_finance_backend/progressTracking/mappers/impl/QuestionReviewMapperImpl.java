package com.example.mynt_finance_backend.progressTracking.mappers.impl;

import com.example.mynt_finance_backend.progressTracking.domain.dtos.QuestionReviewDTO;
import com.example.mynt_finance_backend.progressTracking.domain.entities.QuestionReview;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import org.springframework.stereotype.Component;

@Component
public class QuestionReviewMapperImpl implements Mapper<QuestionReviewDTO, QuestionReview> {

    @Override
    public QuestionReviewDTO mapToDTO(QuestionReview entity) {
        return new QuestionReviewDTO(
                entity.getQuestionProgressTracker().getId(),
                entity.getReviewDate() == null ? null : entity.getReviewDate().toString(),
                entity.getQuestionTime().toMillis(),
                entity.isCorrect(),
                entity.getUserRating()
        );
    }

    @Override
    public QuestionReview mapToEntity(QuestionReviewDTO dto) {
        return new QuestionReview();
    }
}
