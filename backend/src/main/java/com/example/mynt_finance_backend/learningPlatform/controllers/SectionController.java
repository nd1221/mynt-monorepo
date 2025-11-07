package com.example.mynt_finance_backend.learningPlatform.controllers;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.SectionDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.TestDTO;
import com.example.mynt_finance_backend.learningPlatform.services.LessonService;
import com.example.mynt_finance_backend.learningPlatform.services.QuestionService;
import com.example.mynt_finance_backend.learningPlatform.services.SectionService;
import com.example.mynt_finance_backend.learningPlatform.services.TestService;
import com.example.mynt_finance_backend.util.validation.ControllerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sections")
public class SectionController {

    private final SectionService sectionService;

    private final LessonService lessonService;

    private final TestService testService;

    private final QuestionService questionService;

    @Autowired
    public SectionController(SectionService sectionService, LessonService lessonService, TestService testService, QuestionService questionService) {
        this.sectionService = sectionService;
        this.lessonService = lessonService;
        this.testService = testService;
        this.questionService = questionService;
    }

    // CREATE
    @PostMapping()
    public ResponseEntity<SectionDTO> create(@RequestBody SectionDTO sectionDTO) {
        return new ResponseEntity<>(sectionService.createSection(sectionDTO), HttpStatus.CREATED);
    }

    @PostMapping("/{sectionId}/test")
    public ResponseEntity<TestDTO> createTest(@PathVariable("sectionId") Integer sectionId, @RequestBody TestDTO testDTO) {
        return new ResponseEntity<>(testService.createTest(sectionId, testDTO), HttpStatus.CREATED);
    }

    // READ
    @GetMapping("/{id}")
    public ResponseEntity<SectionDTO> readById(@PathVariable("id") Integer id) {
        SectionDTO returnedSection = sectionService.readSectionById(id);
        return returnedSection == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(returnedSection, HttpStatus.OK);
    }

    @GetMapping("/{sectionId}/test")
    public ResponseEntity<TestDTO> readTest(@PathVariable("sectionId") Integer sectionId) {
        ControllerValidator.exists(sectionService::exists, sectionId)
                .and(ControllerValidator.hasSingleDependent(sectionService::hasTest, sectionId))
                .validate();
        return new ResponseEntity<>(testService.readTest(sectionId), HttpStatus.OK);
    }

    @GetMapping()
    public List<SectionDTO> readAll() {
        return sectionService.readAllSections();
    }

    // UPDATE
    @PatchMapping("/{id}")
    public ResponseEntity<SectionDTO> update(@PathVariable("id") Integer id, @RequestBody SectionDTO sectionDTO) {
        ControllerValidator.exists(sectionService::exists, id).validate();
        return new ResponseEntity<>(sectionService.updateSection(sectionDTO, id), HttpStatus.OK);
    }

    @PatchMapping("/{sectionId}/test")
    public ResponseEntity<TestDTO> updateTest(@PathVariable("sectionId") Integer sectionId, @RequestBody TestDTO testDTO) {
        ControllerValidator.exists(sectionService::exists, sectionId)
                .and(ControllerValidator.hasSingleDependent(sectionService::hasTest, sectionId))
                .validate();
        return new ResponseEntity<>(testService.updateTestBySectionId(sectionId, testDTO), HttpStatus.OK);
    }

    @PatchMapping("/{sectionId}/add-lesson/{lessonId}")
    public ResponseEntity<SectionDTO> addLesson(@PathVariable("sectionId") Integer sectionId, @PathVariable("lessonId") Integer lessonId) {
        ControllerValidator.exists(sectionService::exists, sectionId)
                .and(ControllerValidator.exists(lessonService::exists, lessonId))
                .and(ControllerValidator.hasNoChild(sectionService::hasLesson, sectionId, lessonId))
                .validate();
        return new ResponseEntity<>(sectionService.addLesson(sectionId, lessonId), HttpStatus.OK);
    }

    @PatchMapping("/{sectionId}/remove-lesson/{lessonId}")
    public ResponseEntity<SectionDTO> removeLesson(@PathVariable("sectionId") Integer sectionId, @PathVariable("lessonId") Integer lessonId) {
        ControllerValidator.exists(sectionService::exists, sectionId)
                .and(ControllerValidator.exists(lessonService::exists, lessonId))
                .and(ControllerValidator.hasChild(sectionService::hasLesson, sectionId, lessonId))
                .validate();
        return new ResponseEntity<>(sectionService.removeLesson(sectionId, lessonId), HttpStatus.OK);
    }

    // ADD TEST QUESTION
    @PatchMapping("/{sectionId}/add-test-question/{questionId}")
    public ResponseEntity<TestDTO> addTestQuestion(@PathVariable("sectionId") Integer sectionId, @PathVariable("questionId") Long questionId) {
        ControllerValidator.exists(sectionService::exists, sectionId)
                .and(ControllerValidator.exists(questionService::exists, questionId))
                .and(ControllerValidator.hasSingleDependent(sectionService::hasTest, sectionId))
                .validate();

        int testId = sectionService.getTestId(sectionId);
        ControllerValidator.hasNoChild(testService::hasQuestion, testId, questionId).validate();
        return new ResponseEntity<>(testService.addQuestion(testId, questionId), HttpStatus.OK);
    }

    // REMOVE TEST QUESTION
    @PatchMapping("/{sectionId}/remove-test-question/{questionId}")
    public ResponseEntity<TestDTO> removeTestQuestion(@PathVariable("sectionId") Integer sectionId, @PathVariable("questionId") Long questionId) {
        ControllerValidator.exists(sectionService::exists, sectionId)
                .and(ControllerValidator.exists(questionService::exists, questionId))
                .and(ControllerValidator.hasSingleDependent(sectionService::hasTest, sectionId))
                .validate();

        int testId = sectionService.getTestId(sectionId);
        ControllerValidator.hasChild(testService::hasQuestion, testId, questionId).validate();
        return new ResponseEntity<>(testService.removeQuestion(testId, questionId), HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        ControllerValidator.exists(sectionService::exists, id).validate();
        sectionService.deleteSection(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{sectionId}/test")
    public ResponseEntity<SectionDTO> deleteTest(@PathVariable("sectionId") Integer sectionId) {
        ControllerValidator.exists(sectionService::exists, sectionId)
                .and(ControllerValidator.hasSingleDependent(sectionService::hasTest, sectionId))
                .validate();
        return new ResponseEntity<>(sectionService.deleteTest(sectionId), HttpStatus.NO_CONTENT);
    }
}
