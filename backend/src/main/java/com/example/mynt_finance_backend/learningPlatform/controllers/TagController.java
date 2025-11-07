package com.example.mynt_finance_backend.learningPlatform.controllers;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.TagDTO;
import com.example.mynt_finance_backend.learningPlatform.services.TagService;
import com.example.mynt_finance_backend.util.validation.ControllerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    // CREATE
    @PostMapping()
    public ResponseEntity<TagDTO> create(@RequestBody TagDTO tagDTO) {
        return new ResponseEntity<>(tagService.createTag(tagDTO), HttpStatus.CREATED);
    }

    // READ
    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> readById(@PathVariable("id") Integer id) {
        ControllerValidator.exists(tagService::exists, id).validate();
        return new ResponseEntity<>(tagService.readTagById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TagDTO>> readAll() {
        return new ResponseEntity<>(tagService.readAllTags(), HttpStatus.OK);
    }

    // UPDATE
    @PatchMapping("/{id}")
    public ResponseEntity<TagDTO> update(@PathVariable("id") Integer id, @RequestBody TagDTO tagDTO) {
        ControllerValidator.exists(tagService::exists, id).validate();
        return new ResponseEntity<>(tagService.updateTagById(id, tagDTO), HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        ControllerValidator.exists(tagService::exists, id).validate();
        tagService.deleteTagById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
