package com.example.mynt_finance_backend.progressTracking.mappers.impl;

import com.example.mynt_finance_backend.progressTracking.domain.dtos.UserProgressTrackerDTO;
import com.example.mynt_finance_backend.progressTracking.domain.entities.CourseProgressTracker;
import com.example.mynt_finance_backend.progressTracking.domain.entities.UserProgressTracker;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserProgressTrackerMapperImpl implements Mapper<UserProgressTrackerDTO, UserProgressTracker> {

    @Override
    public UserProgressTrackerDTO mapToDTO(UserProgressTracker userProgressTracker) {
        return new UserProgressTrackerDTO(
                userProgressTracker.getId(),
                getChildEntitySet(
                        userProgressTracker.getCourseProgressTrackers().stream()
                                .filter(CourseProgressTracker::isActive)
                                .collect(Collectors.toSet()),
                        CourseProgressTracker::getId
                ),
                userProgressTracker.getStreak(),
                userProgressTracker.getLongestStreak(),
                userProgressTracker.getMostRecentCourseTrackerId()
        );
    }

    @Override
    public UserProgressTracker mapToEntity(UserProgressTrackerDTO userProgressTrackerDTO) {
        return new UserProgressTracker();
    }
}
