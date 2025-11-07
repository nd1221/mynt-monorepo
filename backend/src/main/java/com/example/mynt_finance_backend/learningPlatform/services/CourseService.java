package com.example.mynt_finance_backend.learningPlatform.services;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.CourseDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.CourseEntity;
import com.example.mynt_finance_backend.util.baseInterfaces.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CourseService extends Service<Integer> {

    boolean hasSection(Integer courseId, Integer sectionId);

    boolean hasTag(Integer courseId, Integer tagId);

    // CREATE
    CourseDTO createCourse(CourseDTO courseDTO);

    // READ
    CourseDTO readCourseById(Integer id);

    List<CourseDTO> readAllCourses(); // Deprecated

    Set<CourseEntity> findAllCourses();

    Set<CourseEntity> findAllCoursesById(Set<Integer> courseIds);

    Optional<CourseEntity> findCourseById(Integer id);

    Page<CourseDTO> readAll(Pageable pageable);

    // UPDATE
    CourseDTO updateCourse(CourseDTO courseDTO, Integer id);

    CourseDTO addSection(Integer courseId, Integer sectionId);

    CourseDTO removeSection(Integer courseId, Integer sectionId);

    CourseDTO addTag(Integer courseId, Integer tagId);

    CourseDTO removeTag(Integer courseId, Integer tagId);

    // DELETE
    void deleteCourseById(Integer id);
}
