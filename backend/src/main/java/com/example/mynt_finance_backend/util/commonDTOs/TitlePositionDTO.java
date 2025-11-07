package com.example.mynt_finance_backend.util.commonDTOs;

public record TitlePositionDTO<Id>(
        Id id,
        String title,
        int position
) {
}