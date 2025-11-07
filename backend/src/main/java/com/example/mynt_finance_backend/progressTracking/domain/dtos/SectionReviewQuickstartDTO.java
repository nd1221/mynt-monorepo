package com.example.mynt_finance_backend.progressTracking.domain.dtos;

import java.util.List;

public record SectionReviewQuickstartDTO(
        int sectionId,
        int sectionPosition,
        String sectionTitle,
        List<ReviewQuickstartDTO> lessonQuickstartData
) {
}
