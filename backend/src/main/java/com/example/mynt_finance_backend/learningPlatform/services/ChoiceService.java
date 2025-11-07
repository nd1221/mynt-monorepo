package com.example.mynt_finance_backend.learningPlatform.services;

import com.example.mynt_finance_backend.learningPlatform.domain.DTOs.ChoiceDTO;
import com.example.mynt_finance_backend.util.baseInterfaces.Service;

import java.util.List;

public interface ChoiceService extends Service<Integer> {

    // CREATE
    ChoiceDTO createChoice(Long questionId, ChoiceDTO choiceDTO);

    // READ
    List<ChoiceDTO> readAllChoices(Long questionId);

    // UPDATE
    ChoiceDTO updateChoice(Long questionId, Integer choiceId, ChoiceDTO choiceDTO);

    //DELETE
    void deleteChoiceById(Integer choiceId);
}
