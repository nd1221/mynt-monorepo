package com.example.mynt_finance_backend.progressTracking.mappers.impl;

import com.example.mynt_finance_backend.progressTracking.domain.dtos.TestReviewDTO;
import com.example.mynt_finance_backend.progressTracking.domain.entities.TestReview;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import org.springframework.stereotype.Component;

@Component
public class TestReviewMapperImpl implements Mapper<TestReviewDTO, TestReview> {

    @Override
    public TestReviewDTO mapToDTO(TestReview entity) {
        return new TestReviewDTO(
                entity.getTestProgressTracker().getId(),
                entity.getDateAttempted().toString(),
                entity.getTestTimeMillis(),
                entity.getScore(),
                entity.getNumberOfQuestionsAttempted(),
                entity.isOutOfTime()
        );
    }

    // Deprecated
    @Override
    public TestReview mapToEntity(TestReviewDTO dto) {
        return new TestReview();
    }
}
