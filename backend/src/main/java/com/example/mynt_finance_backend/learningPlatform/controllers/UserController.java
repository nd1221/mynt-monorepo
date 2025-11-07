package com.example.mynt_finance_backend.learningPlatform.controllers;

import com.example.mynt_finance_backend.errorHandling.exceptions.DataIntegrityViolationException;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.UserDTO;
import com.example.mynt_finance_backend.learningPlatform.services.CourseService;
import com.example.mynt_finance_backend.learningPlatform.services.LearningPathService;
import com.example.mynt_finance_backend.learningPlatform.services.UserService;
import com.example.mynt_finance_backend.util.validation.ControllerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final LearningPathService learningPathService;

    private final CourseService courseService;

    @Autowired
    public UserController(UserService userService, LearningPathService learningPathService, CourseService courseService) {
        this.userService = userService;
        this.learningPathService = learningPathService;
        this.courseService = courseService;
    }

    // READ
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> readById(@PathVariable("id") Long id) {
        UserDTO userReturned = userService.readUserById(id);
        return userReturned == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(userReturned, HttpStatus.OK);
    }

    @GetMapping()
    public List<UserDTO> readAll() {
        return userService.readAllUsers();
    }

    // UPDATE
    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable("id") Long id, @RequestBody UserDTO userDTO) {
        ControllerValidator.exists(userService::exists, id).validate();
        return new ResponseEntity<>(userService.updateUser(userDTO, id), HttpStatus.OK);
    }

    @PatchMapping("/{userId}/add-learning-path/{learningPathId}")
    public ResponseEntity<UserDTO> addLearningPath(@PathVariable("userId") Long userId, @PathVariable("learningPathId") Integer learningPathId) {
        ControllerValidator.exists(userService::exists, userId)
                .and(ControllerValidator.exists(learningPathService::exists, learningPathId))
                .and(ControllerValidator.hasNoChild(userService::hasLearningPath, userId, learningPathId))
                .validate();
        return new ResponseEntity<>(userService.addLearningPath(userId, learningPathId), HttpStatus.OK);
    }

    @PatchMapping("/{userId}/remove-learning-path/{learningPathId}")
    public ResponseEntity<UserDTO> removeLearningPath(@PathVariable("userId") Long userId, @PathVariable("learningPathId") Integer learningPathId) {
        ControllerValidator.exists(userService::exists, userId)
                .and(ControllerValidator.exists(learningPathService::exists, learningPathId))
                .and(ControllerValidator.hasChild(userService::hasLearningPath, userId, learningPathId))
                .validate();
        return new ResponseEntity<>(userService.removeLearningPath(userId, learningPathId), HttpStatus.OK);
    }

    @PatchMapping("/{userId}/add-course/{courseId}")
    public ResponseEntity<UserDTO> addCourse(@PathVariable("userId") Long userId, @PathVariable("courseId") Integer courseId) {
        ControllerValidator.exists(userService::exists, userId)
                .and(ControllerValidator.exists(courseService::exists, courseId))
                .and(ControllerValidator.hasNoChild(userService::hasCourse, userId, courseId))
                .validate();
        return new ResponseEntity<>(userService.addCourse(userId, courseId), HttpStatus.OK);
    }

    @PatchMapping("/{userId}/remove-course/{courseId}")
    public ResponseEntity<?> removeCourse(@PathVariable("userId") Long userId, @PathVariable("courseId") Integer courseId) {
        ControllerValidator.exists(userService::exists, userId)
                .and(ControllerValidator.exists(courseService::exists, courseId))
                .and(ControllerValidator.hasChild(userService::hasCourse, userId, courseId))
                .validate();
        try {
            return new ResponseEntity<>(userService.removeCourse(userId, courseId), HttpStatus.OK);
        } catch (DataIntegrityViolationException dive) {
            return new ResponseEntity<>(dive.getMessage(), HttpStatus.CONFLICT);
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        ControllerValidator.exists(userService::exists, id).validate();
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
