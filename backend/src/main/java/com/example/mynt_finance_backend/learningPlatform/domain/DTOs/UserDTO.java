package com.example.mynt_finance_backend.learningPlatform.domain.DTOs;

import com.example.mynt_finance_backend.learningPlatform.domain.entityEnums.UserRole;

import java.time.LocalDateTime;
import java.util.Set;

public record UserDTO(
        Long id,
        String password,
        String email,
        UserRole role,
        Set<Integer> learningPathIds,
        Set<Integer> courseIds,
        LocalDateTime createdAt
) {
}
