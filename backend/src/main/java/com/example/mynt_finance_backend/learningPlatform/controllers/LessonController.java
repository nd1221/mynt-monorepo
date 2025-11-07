package com.example.mynt_finance_backend.learningPlatform.controllers;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.LessonContentDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.LessonDTO;
import com.example.mynt_finance_backend.learningPlatform.services.LessonService;
import com.example.mynt_finance_backend.learningPlatform.services.QuestionService;
import com.example.mynt_finance_backend.util.validation.ControllerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService lessonService;

    private final QuestionService questionService;

    @Autowired
    public LessonController(LessonService lessonService, QuestionService questionService) {
        this.lessonService = lessonService;
        this.questionService = questionService;
    }

    // CREATE
    @PostMapping()
    public ResponseEntity<LessonDTO> create(@RequestBody LessonDTO lessonDTO) {
        return new ResponseEntity<>(lessonService.createLesson(lessonDTO), HttpStatus.CREATED);
    }

    // READ
    @GetMapping("/{id}")
    public ResponseEntity<LessonDTO> read(@PathVariable("id") Integer id) {
        LessonDTO returnedLesson = lessonService.readLessonById(id);
        return returnedLesson == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(returnedLesson, HttpStatus.OK);
    }

    @GetMapping()
    public List<LessonDTO> readAll() {
        return lessonService.readAllLessons();
    }

    @GetMapping("/{id}/content")
    public ResponseEntity<LessonContentDTO> getContent(@PathVariable("id") Integer id) {
        // Error handling relating to null content is specified on frontend
        return new ResponseEntity<>(lessonService.getLessonContent(id), HttpStatus.OK);
    }

    // UPDATE
    @PatchMapping("/{id}")
    public ResponseEntity<LessonDTO> update(@PathVariable("id") Integer id, @RequestBody LessonDTO lessonDTO) {
        ControllerValidator.exists(lessonService::exists, id).validate();
        return new ResponseEntity<>(lessonService.updateLesson(lessonDTO, id), HttpStatus.OK);
    }

    @PatchMapping("/{lessonId}/add-question/{questionId}")
    public ResponseEntity<LessonDTO> addQuestion(@PathVariable("lessonId") Integer lessonId, @PathVariable("questionId") Long questionId) {
        ControllerValidator.exists(lessonService::exists, lessonId)
                .and(ControllerValidator.exists(questionService::exists, questionId))
                .and(ControllerValidator.hasNoChild(lessonService::hasQuestion, lessonId, questionId))
                .validate();
        return new ResponseEntity<>(lessonService.addQuestion(lessonId, questionId), HttpStatus.OK);
    }

    @PatchMapping("/{lessonId}/remove-question/{questionId}")
    public ResponseEntity<LessonDTO> removeQuestion(@PathVariable("lessonId") Integer lessonId, @PathVariable("questionId") Long questionId) {
        ControllerValidator.exists(lessonService::exists, lessonId)
                .and(ControllerValidator.exists(questionService::exists, questionId))
                .and(ControllerValidator.hasChild(lessonService::hasQuestion, lessonId, questionId))
                .validate();
        return new ResponseEntity<>(lessonService.removeQuestion(lessonId, questionId), HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        ControllerValidator.exists(lessonService::exists, id).validate();
        lessonService.deleteLesson(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
