package com.example.mynt_finance_backend.progressTracking.controllers.utils;

public record ReviewSessionSearchParam(
        Integer section,
        Integer lesson,
        Integer numberRequested,
        Integer difficulty,
        Boolean includeUnseen,
        Double slider
) {
}
