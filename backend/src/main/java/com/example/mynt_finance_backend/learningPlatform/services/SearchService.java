package com.example.mynt_finance_backend.learningPlatform.services;

import com.example.mynt_finance_backend.learningPlatform.controllers.utils.SearchQueryFilter;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.CourseDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.LearningPathDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchService {

    // READ
    Page<CourseDTO> readFilteredCourses(SearchQueryFilter filter, Pageable pageable);

    Page<LearningPathDTO> readFilteredLearningPaths(SearchQueryFilter searchQueryFilter, Pageable pageable);
}
