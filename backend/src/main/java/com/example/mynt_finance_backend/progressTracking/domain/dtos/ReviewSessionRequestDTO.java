package com.example.mynt_finance_backend.progressTracking.domain.dtos;

import java.util.List;

public record ReviewSessionRequestDTO(
        List<Long> questionTrackerIds,
        List<Long> unseenQuestionIds
) {
}
