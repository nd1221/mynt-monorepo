package com.example.mynt_finance_backend.learningPlatform.controllers;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.ChoiceDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.MultipleChoiceQuestionDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.QuestionDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.TrueFalseQuestionDTO;
import com.example.mynt_finance_backend.learningPlatform.services.ChoiceService;
import com.example.mynt_finance_backend.learningPlatform.services.QuestionService;
import com.example.mynt_finance_backend.util.validation.ControllerValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    private final ChoiceService choiceService;

    public QuestionController(QuestionService questionService, ChoiceService choiceService) {
        this.questionService = questionService;
        this.choiceService = choiceService;
    }

    // CREATE
    @PostMapping("/multiple-choice")
    public ResponseEntity<MultipleChoiceQuestionDTO> create(@RequestBody MultipleChoiceQuestionDTO multipleChoiceQuestionDTO) {
        return new ResponseEntity<>(questionService.createQuestion(multipleChoiceQuestionDTO), HttpStatus.CREATED);
    }

    @PostMapping("/multiple-choice/{id}/choices")
    public ResponseEntity<ChoiceDTO> create(@PathVariable("id") Long id, @RequestBody ChoiceDTO choiceDTO) {
        ControllerValidator.exists(questionService::multipleChoiceQuestionExists, id).validate();
        return new ResponseEntity<>(choiceService.createChoice(id, choiceDTO), HttpStatus.CREATED);
    }

    @PostMapping("/true-false")
    public ResponseEntity<TrueFalseQuestionDTO> create(@RequestBody TrueFalseQuestionDTO trueFalseQuestionDTO) {
        return new ResponseEntity<>(questionService.createQuestion(trueFalseQuestionDTO), HttpStatus.CREATED);
    }

    // READ
    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> readQuestion(@PathVariable("id") Long id) {
        ControllerValidator.exists(questionService::exists, id).validate();
        return new ResponseEntity<>(questionService.readQuestionById(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<QuestionDTO>> readAll() {
        return new ResponseEntity<>(questionService.readAllQuestions(), HttpStatus.OK);
    }

    @GetMapping("/multiple-choice")
    public ResponseEntity<List<MultipleChoiceQuestionDTO>> readAllMultipleChoice() {
        return new ResponseEntity<>(questionService.readAllMultipleChoiceQuestions(), HttpStatus.OK);
    }

    @GetMapping("/multiple-choice/{id}/choices")
    public ResponseEntity<List<ChoiceDTO>> readAllChoices(@PathVariable("id") Long id) {
        ControllerValidator.exists(questionService::multipleChoiceQuestionExists, id).validate();
        return new ResponseEntity<>(choiceService.readAllChoices(id), HttpStatus.OK);
    }

    @GetMapping("/true-false")
    public ResponseEntity<List<TrueFalseQuestionDTO>> readAllTrueFalse() {
        return new ResponseEntity<>(questionService.readAllTrueFalseQuestions(), HttpStatus.OK);
    }

    // UPDATE
    @PatchMapping("/multiple-choice/{id}")
    public ResponseEntity<MultipleChoiceQuestionDTO> update(@PathVariable("id") Long id, @RequestBody MultipleChoiceQuestionDTO questionDTO) {
        ControllerValidator.exists(questionService::multipleChoiceQuestionExists, id).validate();
        return new ResponseEntity<>(questionService.updateMultipleChoiceQuestion(id, questionDTO), HttpStatus.OK);
    }

    @PatchMapping("/multiple-choice/{questionId}/choices/{choiceId}")
    public ResponseEntity<ChoiceDTO> updateChoice(@PathVariable("questionId") Long questionId, @PathVariable("choiceId") Integer choiceId, @RequestBody ChoiceDTO choiceDTO) {
        ControllerValidator.exists(questionService::multipleChoiceQuestionExists, questionId)
                .and(ControllerValidator.exists(choiceService::exists, choiceId))
                .validate();
        return new ResponseEntity<>(choiceService.updateChoice(questionId, choiceId, choiceDTO), HttpStatus.OK);
    }

    @PatchMapping("/true-false/{id}")
    public ResponseEntity<TrueFalseQuestionDTO> update(@PathVariable("id") Long id, @RequestBody TrueFalseQuestionDTO questionDTO) {
        ControllerValidator.exists(questionService::trueFalseQuestionExists, id).validate();
        return new ResponseEntity<>(questionService.updateTrueFalseQuestion(id, questionDTO), HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        ControllerValidator.exists(questionService::exists, id).validate();
        questionService.deleteQuestionById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/multiple-choice/{questionId}/choices/{choiceId}")
    public ResponseEntity<Void> deleteChoiceById(@PathVariable("questionId") Long questionId, @PathVariable("choiceId") Integer choiceId) {
        ControllerValidator.exists(questionService::exists, questionId)
                .and(ControllerValidator.exists(choiceService::exists, choiceId))
                .validate();
        choiceService.deleteChoiceById(choiceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
