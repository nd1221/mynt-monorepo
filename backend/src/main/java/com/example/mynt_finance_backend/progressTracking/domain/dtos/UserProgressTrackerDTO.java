package com.example.mynt_finance_backend.progressTracking.domain.dtos;

import java.util.*;

public record UserProgressTrackerDTO(
        Long id,
        Set<Long> courseProgressTrackerIds,
        int streak,
        int longestStreak,
        Long mostRecentCourseTrackerId
) {
}
