package com.example.mynt_finance_backend.progressTracking.mappers.impl;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.SectionEntity;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.TestProgressTrackerDTO;
import com.example.mynt_finance_backend.progressTracking.domain.entities.TestProgressTracker;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import org.springframework.stereotype.Component;

@Component
public class TestProgressTrackerMapperImpl implements Mapper<TestProgressTrackerDTO, TestProgressTracker> {

    @Override
    public TestProgressTrackerDTO mapToDTO(TestProgressTracker entity) {
        return new TestProgressTrackerDTO(
                entity.getId(),
                getChildEntityId(entity.getSection(), SectionEntity::getId).get(),
                entity.getAveragePercentage(),
                entity.getAverageTestTimeMillis(),
                entity.getOutOfTimeCount(),
                entity.getAveragePercentQuestionsAttempted(),
                entity.getFirstAttempted() == null ? null : entity.getFirstAttempted().toString(),
                entity.getLastAttempted() == null ? null : entity.getLastAttempted().toString(),
                entity.getNumberOfAttempts()
        );
    }

    // Should be deprecated
    @Override
    public TestProgressTracker mapToEntity(TestProgressTrackerDTO dto) {
        return new TestProgressTracker();
    }
}
