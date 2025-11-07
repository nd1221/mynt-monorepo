package com.example.mynt_finance_backend.progressTracking.services.impl;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.UserEntity;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.CourseTrackerSearchSummaryDTO;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.UserProgressTrackerDTO;
import com.example.mynt_finance_backend.progressTracking.domain.entities.CourseProgressTracker;
import com.example.mynt_finance_backend.progressTracking.domain.entities.UserProgressTracker;
import com.example.mynt_finance_backend.progressTracking.repositories.UserProgressTrackerRepository;
import com.example.mynt_finance_backend.progressTracking.services.UserProgressTrackerService;
import com.example.mynt_finance_backend.progressTracking.services.intermediaries.ProgressTrackingServicesFacade;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class UserProgressTrackerServiceImpl implements UserProgressTrackerService {

    private final UserProgressTrackerRepository userProgressTrackerRepository;

    private final Mapper<UserProgressTrackerDTO, UserProgressTracker> mapper;

    private final ProgressTrackingServicesFacade progressTrackingServicesFacade;

    @Autowired
    public UserProgressTrackerServiceImpl(UserProgressTrackerRepository userProgressTrackerRepository, Mapper<UserProgressTrackerDTO, UserProgressTracker> mapper, @Lazy ProgressTrackingServicesFacade progressTrackingServicesFacade) {
        this.userProgressTrackerRepository = userProgressTrackerRepository;
        this.mapper = mapper;
        this.progressTrackingServicesFacade = progressTrackingServicesFacade;
    }

    @Override
    public boolean exists(Long id) {
        return userProgressTrackerRepository.existsById(id);
    }

    @Override
    public void createUserProgressTracker(UserEntity user) {

        UserProgressTracker newUserProgressTracker = new UserProgressTracker();
        newUserProgressTracker.setUser(user);
        user.setUserProgressTracker(newUserProgressTracker);

        newUserProgressTracker.setCourseProgressTrackers(new HashSet<>());
        newUserProgressTracker.setMostRecentCourseId(null);
        newUserProgressTracker.setStreak(0);
        newUserProgressTracker.setLongestStreak(0);
        newUserProgressTracker.setLastPracticeDate(null);

        userProgressTrackerRepository.save(newUserProgressTracker);
    }

    @Override
    public UserProgressTrackerDTO getUserProgressTrackerById(Long userProgressTrackerId) {
        return mapper.mapToDTO(userProgressTrackerRepository.findById(userProgressTrackerId).get());
    }

    @Override
    public UserProgressTrackerDTO getUserProgressTrackerByUserId(Long userId) {
        return mapper.mapToDTO(userProgressTrackerRepository.findByUserId(userId));
    }

    @Override
    public UserProgressTracker findUserProgressTrackerById(Long userProgressTrackerId) {
        return userProgressTrackerRepository.findById(userProgressTrackerId).get();
    }

    @Override
    public void setMostRecentCourseTracker(long userProgressTrackerId, long trackerId) {
        UserProgressTracker upt = userProgressTrackerRepository.findById(userProgressTrackerId).get();
        if (upt.getCourseProgressTrackers().stream().map(CourseProgressTracker::getId).toList().contains(trackerId)) {
            upt.setMostRecentCourseId(trackerId);
        }
    }

    @Override
    public void setMostRecentCourseTrackerAfterDeactivation(long userProgressTrackerId) {

        // To be used only after user has unenrolled from a course and deactivated their progress tracker
        UserProgressTracker upt = userProgressTrackerRepository.findById(userProgressTrackerId).get();
        Long mostRecentCourse = upt.getCourseProgressTrackers().stream()
                .filter(CourseProgressTracker::isActive)
                .sorted(
                        Comparator.comparing(
                                CourseProgressTracker::getLastPracticeDate
                        ).thenComparing(
                                CourseProgressTracker::getId
                        )
                )
                .map(CourseProgressTracker::getId)
                .findFirst()
                .orElse(null);

        upt.setMostRecentCourseId(mostRecentCourse);
    }

    @Override
    public List<CourseTrackerSearchSummaryDTO> getCourseTrackerSearchSummaries(long userProgressTrackerId) {
        UserProgressTracker upt = userProgressTrackerRepository.findById(userProgressTrackerId).get();
        return upt.getCourseProgressTrackers().stream()
                .filter(CourseProgressTracker::isActive)
                .map(CourseProgressTracker::getId)
                .map(progressTrackingServicesFacade::getCourseTrackerSummary)
                .filter(Objects::nonNull)
                .toList();
    }
}
