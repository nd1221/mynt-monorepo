package com.example.mynt_finance_backend.learningPlatform.services.impl;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.CourseDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.CourseEntity;
import com.example.mynt_finance_backend.util.baseInterfaces.Mapper;
import com.example.mynt_finance_backend.learningPlatform.repositories.CourseRepository;
import com.example.mynt_finance_backend.learningPlatform.services.CourseService;
import com.example.mynt_finance_backend.learningPlatform.services.intermediaries.LearningPlatformServicesFacade;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private final Mapper<CourseDTO, CourseEntity> courseMapper;

    private final LearningPlatformServicesFacade learningPlatformServicesFacade;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, Mapper<CourseDTO, CourseEntity> courseMapper, @Lazy LearningPlatformServicesFacade learningPlatformServicesFacade) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.learningPlatformServicesFacade = learningPlatformServicesFacade;
    }

    @Override
    public boolean exists(Integer id) {
        return courseRepository.existsById(id);
    }

    @Override
    public boolean hasSection(Integer courseId, Integer sectionId) {
        return courseRepository.courseContainsSection(courseId, sectionId);
    }

    @Override
    public boolean hasTag(Integer courseId, Integer tagId) {
        return courseRepository.courseContainsTag(courseId, tagId);
    }

    // CREATE
    @Override
    public CourseDTO createCourse(CourseDTO courseDTO) {
        CourseEntity courseEntity = courseMapper.mapToEntity(courseDTO);
        return courseMapper.mapToDTO(courseRepository.save(courseEntity));
    }

    // READ
    @Override
    public CourseDTO readCourseById(Integer id) {
        Optional<CourseEntity> courseEntity = courseRepository.findById(id);
        return courseEntity.map(courseMapper::mapToDTO).orElse(null);
    }

    @Override
    public List<CourseDTO> readAllCourses() {
        List<CourseEntity> courses = courseRepository.findAll();
        return courses.stream().map(courseMapper::mapToDTO).toList();
    }

    @Override
    public Set<CourseEntity> findAllCourses() {
        return new HashSet<>(courseRepository.findAll());
    }

    @Override
    public Set<CourseEntity> findAllCoursesById(Set<Integer> courseIds) {
        return courseIds == null ?
                null :
                new HashSet<>(courseRepository.findAllById(courseIds));
    }

    @Override
    public Optional<CourseEntity> findCourseById(Integer id) {
        return courseRepository.findById(id);
    }

    @Override
    public Page<CourseDTO> readAll(Pageable pageable) {
        Page<CourseEntity> courseEntities = courseRepository.findAll(pageable);
        return courseEntities.map(courseMapper::mapToDTO);
    }

    // UPDATE
    @Override
    public CourseDTO updateCourse(CourseDTO courseDTO, Integer id) {

        CourseEntity existingEntity = courseRepository.findById(id).get();

        updateFieldIfNotNull(existingEntity::setTitle, courseDTO.title());
        updateFieldIfNotNull(existingEntity::setDescription, courseDTO.description());
        updateFieldIfNotNull(existingEntity::setDifficulty, courseDTO.difficulty());
        updateFieldIfNotNull(existingEntity::setCreators, courseDTO.creators());
        updateFieldIfNotNull(existingEntity::setIconURL, courseDTO.iconURL());
        existingEntity.update();

        CourseEntity course = courseRepository.save(existingEntity);
        return courseMapper.mapToDTO(course);
    }

    @Override
    public CourseDTO addSection(Integer courseId, Integer sectionId) {
        CourseEntity course = courseRepository.findById(courseId).get();
        learningPlatformServicesFacade.addSectionToCourse(course, sectionId);
        course.update();
        return courseMapper.mapToDTO(course);
    }

    @Override
    public CourseDTO removeSection(Integer courseId, Integer sectionId) {
        CourseEntity course = courseRepository.findById(courseId).get();
        learningPlatformServicesFacade.removeSectionFromCourse(course, sectionId);
        course.update();
        return courseMapper.mapToDTO(course);
    }

    @Override
    public CourseDTO addTag(Integer courseId, Integer tagId) {
        CourseEntity course = courseRepository.findById(courseId).get();
        learningPlatformServicesFacade.addTagToCourse(course, tagId);
        return courseMapper.mapToDTO(course);
    }

    @Override
    public CourseDTO removeTag(Integer courseId, Integer tagId) {
        CourseEntity course = courseRepository.findById(courseId).get();
        learningPlatformServicesFacade.removeTagFromCourse(course, tagId);
        return courseMapper.mapToDTO(course);
    }

    // DELETE
    @Override
    public void deleteCourseById(Integer id) {
        CourseEntity course = courseRepository.findById(id).get();
        learningPlatformServicesFacade.deleteCourseChildren(course);
        learningPlatformServicesFacade.deleteCourseParentAssociations(course);
        courseRepository.deleteById(id);
    }
}
