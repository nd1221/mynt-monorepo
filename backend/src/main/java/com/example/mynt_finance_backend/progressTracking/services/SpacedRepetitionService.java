package com.example.mynt_finance_backend.progressTracking.services;

import com.example.mynt_finance_backend.progressTracking.domain.entities.QuestionProgressTracker;

public interface SpacedRepetitionService {

    void runAlgorithm(QuestionProgressTracker qpt);
}
