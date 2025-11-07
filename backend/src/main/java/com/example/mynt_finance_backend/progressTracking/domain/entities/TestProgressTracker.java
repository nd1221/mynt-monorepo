package com.example.mynt_finance_backend.progressTracking.domain.entities;

import com.example.mynt_finance_backend.learningPlatform.domain.entities.SectionEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TestEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "test_progress_trackers")
public class TestProgressTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(
            targetEntity = CourseProgressTracker.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "course_progress_tracker_id")
    private CourseProgressTracker courseProgressTracker;

    @ManyToOne(
            targetEntity = TestEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "test_id")
    private TestEntity test;

    @OneToOne(
            targetEntity = SectionEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "section_id")
    private SectionEntity section;

    private Double averagePercentage;

    private Long averageTestTimeMillis;

    private Integer outOfTimeCount;

    private Double averagePercentQuestionsAttempted;

    private LocalDateTime lastAttempted;

    private LocalDateTime firstAttempted;

    private Integer numberOfAttempts;

    public TestProgressTracker() {
    }

    public TestProgressTracker(Long id, CourseProgressTracker courseProgressTracker, TestEntity test, SectionEntity section, Double averagePercentage, Long averageTestTimeMillis, Integer outOfTimeCount, Double averagePercentQuestionsAttempted, LocalDateTime lastAttempted, LocalDateTime firstAttempted, Integer numberOfAttempts) {
        this.id = id;
        this.courseProgressTracker = courseProgressTracker;
        this.test = test;
        this.section = section;
        this.averagePercentage = averagePercentage;
        this.averageTestTimeMillis = averageTestTimeMillis;
        this.outOfTimeCount = outOfTimeCount;
        this.averagePercentQuestionsAttempted = averagePercentQuestionsAttempted;
        this.lastAttempted = lastAttempted;
        this.firstAttempted = firstAttempted;
        this.numberOfAttempts = numberOfAttempts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CourseProgressTracker getCourseProgressTracker() {
        return courseProgressTracker;
    }

    public void setCourseProgressTracker(CourseProgressTracker courseProgressTracker) {
        this.courseProgressTracker = courseProgressTracker;
    }

    public TestEntity getTest() {
        return test;
    }

    public void setTest(TestEntity test) {
        this.test = test;
    }

    public Double getAveragePercentage() {
        return averagePercentage;
    }

    public void setAveragePercentage(Double averagePercentage) {
        this.averagePercentage = averagePercentage;
    }

    public Long getAverageTestTimeMillis() {
        return averageTestTimeMillis;
    }

    public void setAverageTestTimeMillis(Long averageTestTimeMillis) {
        this.averageTestTimeMillis = averageTestTimeMillis;
    }

    public Integer getOutOfTimeCount() {
        return outOfTimeCount;
    }

    public void setOutOfTimeCount(Integer outOfTimeCount) {
        this.outOfTimeCount = outOfTimeCount;
    }

    public void incrementOutOfTimeCount() {
        this.outOfTimeCount++;
    }

    public Double getAveragePercentQuestionsAttempted() {
        return averagePercentQuestionsAttempted;
    }

    public void setAveragePercentQuestionsAttempted(Double averageQuestionsAttempted) {
        this.averagePercentQuestionsAttempted = averageQuestionsAttempted;
    }

    public LocalDateTime getLastAttempted() {
        return lastAttempted;
    }

    public void setLastAttempted(LocalDateTime lastAttempted) {
        this.lastAttempted = lastAttempted;
    }

    public LocalDateTime getFirstAttempted() {
        return firstAttempted;
    }

    public void setFirstAttempted(LocalDateTime firstAttempted) {
        this.firstAttempted = firstAttempted;
    }

    public Integer getNumberOfAttempts() {
        return numberOfAttempts;
    }

    public void setNumberOfAttempts(Integer numberOfAttempts) {
        this.numberOfAttempts = numberOfAttempts;
    }

    public void incrementNumberOfAttempts() {
        this.numberOfAttempts++;
    }

    public SectionEntity getSection() {
        return section;
    }

    public void setSection(SectionEntity section) {
        this.section = section;
    }
}
