package com.example.mynt_finance_backend.util.commonDTOs;

public record EntityTrackerDTO<Entity, ProgressTracker>(
        // Generic dto for entity tracker pairs
        Entity entity,
        ProgressTracker tracker
) {
}
