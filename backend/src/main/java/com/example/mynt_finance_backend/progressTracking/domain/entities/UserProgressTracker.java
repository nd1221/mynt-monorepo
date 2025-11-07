package com.example.mynt_finance_backend.progressTracking.domain.entities;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.UserEntity;
import com.example.mynt_finance_backend.progressTracking.domain.entities.CourseProgressTracker;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_progress_trackers")
public class UserProgressTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(
            targetEntity = UserEntity.class,
            mappedBy = "userProgressTracker",
            fetch = FetchType.LAZY
    )
    private UserEntity user;

    @OneToMany(
            targetEntity = CourseProgressTracker.class,
            mappedBy = "userProgressTracker",
            fetch = FetchType.LAZY
    )
    private Set<CourseProgressTracker> courseProgressTrackers;

    private int streak;

    private int longestStreak;

    private Long mostRecentCourseTrackerId;

    private LocalDate lastPracticeDate;

    public UserProgressTracker() {
    }

    public UserProgressTracker(Long id, UserEntity user, Set<CourseProgressTracker> courseProgressTrackers, int streak, int longestStreak, Long mostRecentCourseTrackerId, LocalDate lastPracticeDate) {
        this.id = id;
        this.user = user;
        this.courseProgressTrackers = courseProgressTrackers;
        this.streak = streak;
        this.longestStreak = longestStreak;
        this.mostRecentCourseTrackerId = mostRecentCourseTrackerId;
        this.lastPracticeDate = lastPracticeDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Set<CourseProgressTracker> getCourseProgressTrackers() {
        return courseProgressTrackers;
    }

    public void setCourseProgressTrackers(Set<CourseProgressTracker> courseProgressTrackers) {
        this.courseProgressTrackers = courseProgressTrackers;
    }

    public void addCourseProgressTracker(CourseProgressTracker cpt) {
        if (this.courseProgressTrackers == null) {
            this.courseProgressTrackers = new HashSet<>();
        }
        this.courseProgressTrackers.add(cpt);
    }

    public void removeCourseProgressTracker(CourseProgressTracker cpt) {
        if (this.courseProgressTrackers != null) {
            this.courseProgressTrackers.remove(cpt);
        }
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public int getLongestStreak() {
        return longestStreak;
    }

    public void setLongestStreak(int longestStreak) {
        this.longestStreak = longestStreak;
    }

    public Long getMostRecentCourseTrackerId() {
        return this.mostRecentCourseTrackerId;
    }

    public void setMostRecentCourseId(Long mostRecentCourseTrackerId) {
        this.mostRecentCourseTrackerId = mostRecentCourseTrackerId;
    }

    public LocalDate getLastPracticeDate() {
        return lastPracticeDate;
    }

    public void setLastPracticeDate(LocalDate lastPracticeDate) {
        this.lastPracticeDate = lastPracticeDate;
    }
}
