package com.example.mynt_finance_backend.authentication.domain.DTOs;

import java.util.Set;

public record SessionDTO(
        Long userId,
        boolean isAuthenticated,
        String email,
        Set<Integer> learningPathIds,
        Set<Long> courseProgressTrackerIds,
        Set<Integer> courseIds,
        Long userProgressTrackerId
) {
}
