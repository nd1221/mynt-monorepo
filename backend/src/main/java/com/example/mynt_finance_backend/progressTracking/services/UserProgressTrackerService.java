package com.example.mynt_finance_backend.progressTracking.services;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.UserEntity;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.CourseTrackerSearchSummaryDTO;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.UserProgressTrackerDTO;
import com.example.mynt_finance_backend.progressTracking.domain.entities.UserProgressTracker;
import com.example.mynt_finance_backend.util.baseInterfaces.Service;

import java.util.List;

public interface UserProgressTrackerService extends Service<Long> {

    void createUserProgressTracker(UserEntity user);

    UserProgressTrackerDTO getUserProgressTrackerById(Long userProgressTrackerId);

    UserProgressTrackerDTO getUserProgressTrackerByUserId(Long userId);

    UserProgressTracker findUserProgressTrackerById(Long userProgressTrackerId);

    void setMostRecentCourseTracker(long userProgressTrackerId, long trackerId);

    List<CourseTrackerSearchSummaryDTO> getCourseTrackerSearchSummaries(long userProgressTrackerId);

    void setMostRecentCourseTrackerAfterDeactivation(long userProgressTrackerId);
}
