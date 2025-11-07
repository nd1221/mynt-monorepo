package com.example.mynt_finance_backend.learningPlatform.controllers;

import com.example.mynt_finance_backend.util.validation.ControllerValidator;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.CourseDTO;
import com.example.mynt_finance_backend.learningPlatform.services.CourseService;
import com.example.mynt_finance_backend.learningPlatform.services.SectionService;
import com.example.mynt_finance_backend.learningPlatform.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    private final SectionService sectionService;

    private  final TagService tagService;

    @Autowired
    public CourseController(CourseService courseService, SectionService sectionService, TagService tagService) {
        this.courseService = courseService;
        this.sectionService = sectionService;
        this.tagService = tagService;
    }

    // CREATE
    @PostMapping()
    public ResponseEntity<CourseDTO> create(@RequestBody CourseDTO courseDTO) {
        return new ResponseEntity<>(courseService.createCourse(courseDTO), HttpStatus.CREATED);
    }

    // READ
    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> readById(@PathVariable("id") Integer id) {
        CourseDTO returnedCourse = courseService.readCourseById(id);
        return returnedCourse == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(returnedCourse, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Page<CourseDTO>> readAll(Pageable pageable) {
        return new ResponseEntity<>(courseService.readAll(pageable), HttpStatus.OK);
    }

    // UPDATE
    @PatchMapping("/{id}")
    public ResponseEntity<CourseDTO> update(@PathVariable("id") Integer id, @RequestBody CourseDTO courseDTO) {
        ControllerValidator.exists(courseService::exists, id).validate();
        return new ResponseEntity<>(courseService.updateCourse(courseDTO, id), HttpStatus.OK);
    }

    @PatchMapping("/{courseId}/add-section/{sectionId}")
    public ResponseEntity<CourseDTO> addSection(@PathVariable("courseId") Integer courseId, @PathVariable("sectionId") Integer sectionId) {
        ControllerValidator.exists(courseService::exists, courseId)
                .and(ControllerValidator.exists(sectionService::exists, sectionId))
                .and(ControllerValidator.hasNoChild(courseService::hasSection, courseId, sectionId))
                .validate();
        return new ResponseEntity<>(courseService.addSection(courseId, sectionId), HttpStatus.OK);
    }

    @PatchMapping("/{courseId}/remove-section/{sectionId}")
    public ResponseEntity<CourseDTO> removeSection(@PathVariable("courseId") Integer courseId, @PathVariable("sectionId") Integer sectionId) {
        ControllerValidator.exists(courseService::exists, courseId)
                .and(ControllerValidator.exists(sectionService::exists, sectionId))
                .and(ControllerValidator.hasChild(courseService::hasSection, courseId, sectionId))
                .validate();
        return new ResponseEntity<>(courseService.removeSection(courseId, sectionId), HttpStatus.OK);
    }

    @PatchMapping("/{courseId}/add-tag/{tagId}")
    public ResponseEntity<CourseDTO> addTag(@PathVariable("courseId") Integer courseId, @PathVariable("tagId") Integer tagId) {
        ControllerValidator.exists(courseService::exists, courseId)
                .and(ControllerValidator.exists(tagService::exists, tagId))
                .and(ControllerValidator.hasNoChild(courseService::hasTag, courseId, tagId))
                .validate();
        return new ResponseEntity<>(courseService.addTag(courseId, tagId), HttpStatus.OK);
    }

    @PatchMapping("/{courseId}/remove-tag/{tagId}")
    public ResponseEntity<CourseDTO> removeTag(@PathVariable("courseId") Integer courseId, @PathVariable("tagId") Integer tagId) {
        ControllerValidator.exists(courseService::exists, courseId)
                .and(ControllerValidator.exists(tagService::exists, tagId))
                .and(ControllerValidator.hasChild(courseService::hasTag, courseId, tagId))
                .validate();
        return new ResponseEntity<>(courseService.removeTag(courseId, tagId), HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        ControllerValidator.exists(courseService::exists, id).validate();
        courseService.deleteCourseById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
