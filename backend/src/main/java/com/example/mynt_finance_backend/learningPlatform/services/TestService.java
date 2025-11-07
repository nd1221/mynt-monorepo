package com.example.mynt_finance_backend.learningPlatform.services;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.TestDTO;
import com.example.mynt_finance_backend.learningPlatform.domain.entities.TestEntity;
import com.example.mynt_finance_backend.util.baseInterfaces.Service;

public interface TestService extends Service<Integer> {

    boolean hasQuestion(Integer testId, Long questionId);

    // CREATE
    TestDTO createTest(Integer sectionId, TestDTO testDTO);

    // READ
    TestDTO readTest(Integer sectionId);

    TestEntity findById(int testId);

    // UPDATE
    TestDTO updateTestBySectionId(Integer sectionId, TestDTO testDTO);

    TestDTO addQuestion(Integer testId, Long questionId);

    TestDTO removeQuestion(Integer testId, Long questionId);

    // DELETE
    void deleteTest(Integer sectionId, Integer testId);
}
