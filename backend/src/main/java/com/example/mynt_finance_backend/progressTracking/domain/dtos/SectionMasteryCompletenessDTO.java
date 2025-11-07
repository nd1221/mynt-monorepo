package com.example.mynt_finance_backend.progressTracking.domain.dtos;

import java.util.List;

public record SectionMasteryCompletenessDTO(
        int sectionId,
        String sectionTitle,
        int sectionPosition,
        List<LessonMasteryCompletenessDTO> lessonDTOs
) {
}
