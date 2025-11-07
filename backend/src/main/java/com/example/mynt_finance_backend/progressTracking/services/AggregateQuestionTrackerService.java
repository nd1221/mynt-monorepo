package com.example.mynt_finance_backend.progressTracking.services;

import com.example.mynt_finance_backend.progressTracking.domain.dtos.AggregateQuestionTrackerDTO;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.AggregateQuestionUpdateDTO;
import com.example.mynt_finance_backend.progressTracking.domain.entities.AggregateQuestionTracker;
import com.example.mynt_finance_backend.util.baseInterfaces.Service;

public interface AggregateQuestionTrackerService extends Service<Long>, ProgressService {

    void createAggregateQuestionTracker(long questionId);

    AggregateQuestionTrackerDTO getAggregateTrackerByQuestionId(long questionId);

    Long findTotalCourseQuestionsByDifficulty(int courseId, double lowerThreshold, double upperThreshold);

    AggregateQuestionTrackerDTO mapToDTO(AggregateQuestionTracker aqt);

    AggregateQuestionTrackerDTO updateAggregateQuestionTracker(long aggregateTrackerId, AggregateQuestionUpdateDTO updateDTO);

    AggregateQuestionTracker findAggregateQuestionTrackerById(long id);
}
