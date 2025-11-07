package com.example.mynt_finance_backend.progressTracking.mappers.impl;

import com.example.mynt_finance_backend.progressTracking.domain.dtos.CourseProgressTrackerDTO;
import com.example.mynt_finance_backend.progressTracking.domain.entities.CourseProgressTracker;
import com.example.mynt_finance_backend.progressTracking.domain.entities.LessonProgressTracker;
import com.example.mynt_finance_backend.progressTracking.domain.entities.TestProgressTracker;
import com.example.mynt_finance_backend.progressTracking.mappers.CourseProgressTrackerMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class CourseProgressTrackerMapperImpl implements CourseProgressTrackerMapper {

    @Override
    public CourseProgressTrackerDTO mapToDTO(CourseProgressTracker cpt, double mastery, double completeness) {
        return new CourseProgressTrackerDTO(
                cpt.getId(),
                cpt.getUserProgressTracker().getId(),
                cpt.getCourse().getId(),
                mapLessonProgressTrackers(cpt.getLessonProgressTrackers()),
                mapTestProgressTrackers(cpt.getTestProgressTrackers()),
                cpt.getStreak(),
                cpt.getTotalQuestionsReviewed(),
                cpt.getAccuracy(),
                cpt.getAverageQuestionTime().toMillis(),
                cpt.getQuestionsDueToday(),
                cpt.getFirstPracticeDate() == null ? null : cpt.getFirstPracticeDate().toString(),
                cpt.getLastPracticeDate() == null ? null : cpt.getLastPracticeDate().toString(),
                mastery,
                completeness
        );
    }

    private Map<Integer, Long> mapLessonProgressTrackers(Set<LessonProgressTracker> lpts) {
        Map<Integer, Long> map = new HashMap<>();
        for (LessonProgressTracker lpt : lpts) {
            long lessonTrackerId = lpt.getId();
            int lessonId = lpt.getLesson().getId();
            map.put(lessonId, lessonTrackerId);
        }
        return map;
    }

    private Map<Integer, Long> mapTestProgressTrackers(Set<TestProgressTracker> tpts) {
        Map<Integer, Long> map = new HashMap<>();
        for (TestProgressTracker tpt : tpts) {
            long lessonTrackerId = tpt.getId();
            int lessonId = tpt.getTest().getId();
            map.put(lessonId, lessonTrackerId);
        }
        return map;
    }
}