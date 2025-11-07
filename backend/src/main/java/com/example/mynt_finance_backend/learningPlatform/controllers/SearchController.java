package com.example.mynt_finance_backend.learningPlatform.controllers;

import com.example.mynt_finance_backend.learningPlatform.controllers.utils.SearchQueryFilter;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.CourseDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.LearningPathDTO;
import com.example.mynt_finance_backend.learningPlatform.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping()
    public ResponseEntity<?> readFilterQuery(@ModelAttribute SearchQueryFilter filter, Pageable pageable) {
        if (filter.getType().equals("course")) {
            return getFilteredCourses(filter, pageable);
        } else if (filter.getType().equals("learningPath")) {
            return getFilteredLearningPaths(filter, pageable);
        } else {
            return null;
        }
    }

    private ResponseEntity<Page<CourseDTO>> getFilteredCourses(SearchQueryFilter filter, Pageable pageable) {
        return new ResponseEntity<>(searchService.readFilteredCourses(filter, pageable), HttpStatus.OK);
    }

    private ResponseEntity<Page<LearningPathDTO>> getFilteredLearningPaths(SearchQueryFilter filter, Pageable pageable) {
        return new ResponseEntity<>(searchService.readFilteredLearningPaths(filter, pageable), HttpStatus.OK);
    }
}
