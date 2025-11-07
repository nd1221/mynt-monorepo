package com.example.mynt_finance_backend.progressTracking.domain.dtos;

import com.example.mynt_finance_backend.util.commonDTOs.ParentChildrenDTO;
import com.example.mynt_finance_backend.util.commonDTOs.TitlePositionDTO;

import java.util.List;

public record QuestionBankMetadataDTO(
        int courseId,
        String courseName,
        // sections is a List of ParentChildDTOs corresponding to the form ParentChildDTO<Section, Lesson>
        List<ParentChildrenDTO<TitlePositionDTO<Integer>, TitlePositionDTO<Integer>>> sections
) {
}
