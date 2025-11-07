package com.example.mynt_finance_backend.learningPlatform.controllers;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.LearningPathDTO;
import com.example.mynt_finance_backend.learningPlatform.services.CourseService;
import com.example.mynt_finance_backend.learningPlatform.services.LearningPathService;
import com.example.mynt_finance_backend.learningPlatform.services.TagService;
import com.example.mynt_finance_backend.util.validation.ControllerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/learning-paths")
public class LearningPathController {

    private final LearningPathService learningPathService;

    private final CourseService courseService;

    private final TagService tagService;

    @Autowired
    public LearningPathController(LearningPathService learningPathService, CourseService courseService, TagService tagService) {
        this.learningPathService = learningPathService;
        this.courseService = courseService;
        this.tagService = tagService;
    }

    // CREATE
    @PostMapping()
    public ResponseEntity<LearningPathDTO> create(@RequestBody LearningPathDTO learningPathDTO) {
        return new ResponseEntity<>(learningPathService.createLearningPath(learningPathDTO), HttpStatus.CREATED);
    }

    // READ
    @GetMapping("/{id}")
    public ResponseEntity<LearningPathDTO> readById(@PathVariable("id") Integer id) {
        LearningPathDTO returnedLearningPath = learningPathService.readLearningPathById(id);
        return returnedLearningPath == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(returnedLearningPath, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Page<LearningPathDTO>> readAll(Pageable pageable) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication in protected endpoint: " + auth);
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            System.out.println("UNAUTHORIZED HERE BITCH");
        }
        return new ResponseEntity<>(learningPathService.readAll(pageable), HttpStatus.OK);
    }

    // UPDATE
    @PatchMapping("/{id}")
    public ResponseEntity<LearningPathDTO> update(@PathVariable("id") Integer id, @RequestBody LearningPathDTO learningPathDTO) {
        ControllerValidator.exists(learningPathService::exists, id).validate();
        return new ResponseEntity<>(learningPathService.updateLearningPath(learningPathDTO, id), HttpStatus.OK);
    }

    @PatchMapping("/{learningPathId}/add-course/{courseId}")
    public ResponseEntity<LearningPathDTO> addCourse(@PathVariable("learningPathId") Integer learningPathId, @PathVariable("courseId") Integer courseId) {
        ControllerValidator.exists(learningPathService::exists, learningPathId)
                .and(ControllerValidator.exists(courseService::exists, courseId))
                .and(ControllerValidator.hasNoChild(learningPathService::hasCourse, learningPathId, courseId))
                .validate();
        return new ResponseEntity<>(learningPathService.addCourse(learningPathId, courseId), HttpStatus.OK);
    }

    @PatchMapping("/{learningPathId}/remove-course/{courseId}")
    public ResponseEntity<LearningPathDTO> removeCourse(@PathVariable("learningPathId") Integer learningPathId, @PathVariable("courseId") Integer courseId) {
        ControllerValidator.exists(learningPathService::exists, learningPathId)
                .and(ControllerValidator.exists(courseService::exists, courseId))
                .and(ControllerValidator.hasChild(learningPathService::hasCourse, learningPathId, courseId))
                .validate();
        return new ResponseEntity<>(learningPathService.removeCourse(learningPathId, courseId), HttpStatus.OK);
    }

    @PatchMapping("/{learningPathId}/add-tag/{tagId}")
    public ResponseEntity<LearningPathDTO> addTag(@PathVariable("learningPathId") Integer learningPathId, @PathVariable("tagId") Integer tagId) {
        ControllerValidator.exists(learningPathService::exists, learningPathId)
                .and(ControllerValidator.exists(tagService::exists, tagId))
                .and(ControllerValidator.hasNoChild(learningPathService::hasTag, learningPathId, tagId))
                .validate();
        return new ResponseEntity<>(learningPathService.addTag(learningPathId, tagId), HttpStatus.OK);
    }

    @PatchMapping("/{learningPathId}/remove-tag/{tagId}")
    public ResponseEntity<LearningPathDTO> removeTag(@PathVariable("learningPathId") Integer learningPathId, @PathVariable("tagId") Integer tagId) {
        ControllerValidator.exists(learningPathService::exists, learningPathId)
                .and(ControllerValidator.exists(tagService::exists, tagId))
                .and(ControllerValidator.hasChild(learningPathService::hasTag, learningPathId, tagId))
                .validate();
        return new ResponseEntity<>(learningPathService.removeTag(learningPathId, tagId), HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        ControllerValidator.exists(learningPathService::exists, id).validate();
        learningPathService.deleteLearningPath(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
