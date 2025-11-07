package com.example.mynt_finance_backend.jobs.services;

import com.example.mynt_finance_backend.progressTracking.repositories.CourseProgressTrackerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Transactional
public class DeactivationCleanupService {

    private final CourseProgressTrackerRepository repository;

    @Autowired
    public DeactivationCleanupService(CourseProgressTrackerRepository repository) {
        this.repository = repository;
    }

    // Run once per year on Jan 1st
    @Scheduled(cron = "0 0 0 ? * MON")
    public void deleteInactiveCourseTrackersAndChildren() {
        // Deletes all inactive CourseProgressTrackers and their child Tracker entities if they have been inactive for over a year
        repository.deleteInactiveCourseTrackers(LocalDate.now().minusYears(1L));
    }
}
