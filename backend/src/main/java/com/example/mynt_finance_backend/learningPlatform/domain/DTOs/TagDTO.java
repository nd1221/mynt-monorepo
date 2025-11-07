package com.example.mynt_finance_backend.learningPlatform.domain.DTOs;

import java.util.Set;

public record TagDTO(
        Integer id,
        String tag,
        Set<Integer> learningPathIds,
        Set<Integer> courseIds
) {
}
