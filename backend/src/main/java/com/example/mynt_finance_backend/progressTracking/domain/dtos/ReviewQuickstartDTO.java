package com.example.mynt_finance_backend.progressTracking.domain.dtos;

public record ReviewQuickstartDTO(
        int id,
        long trackerId,
        String title,
        int reviewsDueToday
) {
}
