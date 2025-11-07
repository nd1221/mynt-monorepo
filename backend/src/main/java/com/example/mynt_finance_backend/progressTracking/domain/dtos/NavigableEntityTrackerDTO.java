package com.example.mynt_finance_backend.progressTracking.domain.dtos;

import com.example.mynt_finance_backend.util.commonDTOs.EntityTrackerDTO;

public record NavigableEntityTrackerDTO<Entity, Tracker>(
        // Used if an EntityTrackerDTO needs to contain page navigation metadata
        EntityTrackerDTO<Entity, Tracker> data,
        Long prev,
        Long next
) {
}
