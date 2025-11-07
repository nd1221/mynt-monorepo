package com.example.mynt_finance_backend.progressTracking.domain.dtos;

import java.util.List;

public record ReviewSessionMetadataDTO(
        List<Long> questionProgressTrackerIds,
        List<Long> unseenQuestionIds,
        int numberOfCoreQuestions
) {
}
