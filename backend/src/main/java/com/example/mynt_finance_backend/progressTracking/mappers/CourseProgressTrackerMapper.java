package com.example.mynt_finance_backend.progressTracking.mappers;

import com.example.mynt_finance_backend.progressTracking.domain.dtos.CourseProgressTrackerDTO;
import com.example.mynt_finance_backend.progressTracking.domain.entities.CourseProgressTracker;

public interface CourseProgressTrackerMapper {
    CourseProgressTrackerDTO mapToDTO(CourseProgressTracker entity, double mastery, double completeness);
}
