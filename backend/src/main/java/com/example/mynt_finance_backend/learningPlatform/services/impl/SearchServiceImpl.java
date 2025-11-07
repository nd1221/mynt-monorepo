package com.example.mynt_finance_backend.learningPlatform.services.impl;

import com.example.mynt_finance_backend.learningPlatform.controllers.utils.SearchQueryFilter;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.CourseDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.LearningPathDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.CourseEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.LearningPathEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TagEntity;
import com.example.mynt_finance_backend.learningPlatform.domain.entityInterfaces.Enrollable;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import com.example.mynt_finance_backend.learningPlatform.services.CourseService;
import com.example.mynt_finance_backend.learningPlatform.services.LearningPathService;
import com.example.mynt_finance_backend.learningPlatform.services.SearchService;
import com.example.mynt_finance_backend.learningPlatform.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchServiceImpl implements SearchService {

    private final CourseService courseService;

    private final Mapper<CourseDTO, CourseEntity> courseMapper;

    private final LearningPathService learningPathService;

    private final Mapper<LearningPathDTO, LearningPathEntity> learningPathMapper;

    private final TagService tagService;

    @Autowired
    public SearchServiceImpl(CourseService courseService, Mapper<CourseDTO, CourseEntity> courseMapper, LearningPathService learningPathService, Mapper<LearningPathDTO, LearningPathEntity> learningPathMapper, TagService tagService) {
        this.courseService = courseService;
        this.courseMapper = courseMapper;
        this.learningPathService = learningPathService;
        this.learningPathMapper = learningPathMapper;
        this.tagService = tagService;
    }

    @Override
    public Page<CourseDTO> readFilteredCourses(SearchQueryFilter filter, Pageable pageable) {

        Set<CourseEntity> courses = new HashSet<>();

        // Get tagged courses or all courses
        if (filter.getTags() == null) {
            courses.addAll(courseService.findAllCourses());
        } else {
            List<TagEntity> tags = tagService.findAllTagsByValue(filter.getTags());
            for (TagEntity tag : tags) {
                courses.addAll(tag.getCourses());
            }
        }

        // Filter
        List<CourseEntity> filteredCourses = courses.stream()
                .filter(course ->
                        filter.getFrom() == null || course.getCreatedAt().isAfter(filter.getFrom())
                )
                .filter(course ->
                        filter.getDifficulty() == null || filter.getDifficulty().contains(course.getDifficulty())
                )
                .toList();

        // Sort
        List<CourseDTO> sortedList = sortFilteredEntities(filteredCourses, pageable).stream()
                .map(courseMapper::mapToDTO)
                .toList();

        return convertToPageable(sortedList, pageable);
    }

    @Override
    public Page<LearningPathDTO> readFilteredLearningPaths(SearchQueryFilter filter, Pageable pageable) {

        Set<LearningPathEntity> learningPaths = new HashSet<>();

        // Get tagged learning paths or all learning paths
        if (filter.getTags() == null) {
            learningPaths.addAll(learningPathService.findAllLearningPaths());
        } else {
            List<TagEntity> tags = tagService.findAllTagsByValue(filter.getTags());
            for (TagEntity tag : tags) {
                learningPaths.addAll(tag.getLearningPaths());
            }
        }

        // Filter
        List<LearningPathEntity> filteredLearningPaths = learningPaths.stream()
                .filter(learningPath ->
                        filter.getFrom() == null || learningPath.getCreatedAt().isAfter(filter.getFrom())
                )
                .filter(learningPath ->
                        filter.getDifficulty() == null || filter.getDifficulty().contains(learningPath.getDifficulty())
                )
                .toList();

        // Sort
        List<LearningPathDTO> sortedList = sortFilteredEntities(filteredLearningPaths, pageable).stream()
                .map(learningPathMapper::mapToDTO)
                .toList();

        return convertToPageable(sortedList, pageable);
    }

    private <T extends Enrollable> List<T> sortFilteredEntities(List<T> entities, Pageable pageable) {

        if (pageable.getSort().isSorted()) {

            Comparator<Enrollable> comparator = pageable.getSort().stream()
                    .map(this::getComparator)
                    .reduce(Comparator::thenComparing)
                    .orElse((o1, o2) -> 0);

            return entities.stream()
                    .sorted(comparator)
                    .toList();
        }

        // Default, required to make non-sorted requests deterministic
        return entities.stream()
                .sorted(Comparator.comparing(Enrollable::getTitle))
                .toList();
    }

    private Comparator<Enrollable> getComparator(Sort.Order order) {

        Comparator<Enrollable> comparator;

        if (order.getProperty().equals("title")) {
            comparator = Comparator.comparing(Enrollable::getTitle);
        } else if (order.getProperty().equals("date")) {
            comparator = Comparator.comparing(Enrollable::getCreatedAt);
        } else if (order.getProperty().equals("popularity")) {
            comparator = Comparator.comparing(Enrollable::getNumberOfEnrolledUsers);
        } else {
            comparator = (o1, o2) -> 0;
        }

        return order.isDescending() ? comparator.reversed() : comparator;
    }

    private <T> Page<T> convertToPageable(List<T> content, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), content.size());
        return new PageImpl<>(content.subList(start, end), pageable, content.size());
    }
}