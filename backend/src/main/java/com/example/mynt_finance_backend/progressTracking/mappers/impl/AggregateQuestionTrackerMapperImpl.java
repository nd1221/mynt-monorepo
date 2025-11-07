package com.example.mynt_finance_backend.progressTracking.mappers.impl;

import com.example.mynt_finance_backend.progressTracking.domain.dtos.AggregateQuestionTrackerDTO;
import com.example.mynt_finance_backend.progressTracking.domain.entities.AggregateQuestionTracker;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import org.springframework.stereotype.Component;

@Component
public class AggregateQuestionTrackerMapperImpl implements Mapper<AggregateQuestionTrackerDTO, AggregateQuestionTracker> {

    @Override
    public AggregateQuestionTrackerDTO mapToDTO(AggregateQuestionTracker aqt) {
        return new AggregateQuestionTrackerDTO(
                aqt.getId(),
                aqt.getQuestion().getId(),
                aqt.getAccuracy(),
                aqt.getAverageQuestionTime().toMillis(),
                aqt.getNumberOfAttempts()
        );
    }

    @Override
    public AggregateQuestionTracker mapToEntity(AggregateQuestionTrackerDTO dto) {
        return null;
    }
}
