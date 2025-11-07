package com.example.mynt_finance_backend.learningPlatform.domain.entityInterfaces;

import com.example.mynt_finance_backend.learningPlatform.domain.entityEnums.ContentDifficulty;

import java.time.LocalDateTime;

public interface Enrollable {
    // Defines entities representing content users can enroll on to, i.e. learning paths and courses.
    // Mainly for convenience and DRY for SearchController functionality

    String getTitle();

    Integer getNumberOfEnrolledUsers();

    LocalDateTime getCreatedAt();
}
