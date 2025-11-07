package com.example.mynt_finance_backend.progressTracking.services;

import com.example.mynt_finance_backend.progressTracking.controllers.utils.QuestionBankSearchParam;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.QuestionBankPageDTO;
import org.springframework.data.domain.Pageable;

public interface QuestionBankService {

    QuestionBankPageDTO getFilteredQuestions(Pageable pageable, QuestionBankSearchParam searchParams, long courseProgressTrackerId);
}
