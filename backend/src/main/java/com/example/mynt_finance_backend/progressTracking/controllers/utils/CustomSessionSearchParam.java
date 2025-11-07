package com.example.mynt_finance_backend.progressTracking.controllers.utils;

public record CustomSessionSearchParam(
        Integer section,
        Integer lesson,
        Integer numberRequested,
        Integer difficulty,
        Double slider,
        Integer priority
) {
}
