package com.example.mynt_finance_backend.progressTracking.controllers.utils;

public record QuestionBankSearchParam(
        Integer section,
        Integer lesson,
        String core,
        String answered,
        String difficulty
) {
}
