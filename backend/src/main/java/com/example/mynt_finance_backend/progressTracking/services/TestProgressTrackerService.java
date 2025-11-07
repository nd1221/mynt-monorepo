package com.example.mynt_finance_backend.progressTracking.services;

import com.example.mynt_finance_backend.progressTracking.domain.dtos.TestProgressTrackerDTO;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.TestProgressUpdateDTO;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.TestReviewDTO;
import com.example.mynt_finance_backend.util.baseInterfaces.Service;

import java.util.List;

public interface TestProgressTrackerService extends Service<Long>, ProgressService {

    TestProgressTrackerDTO readByCourseProgressTrackerAndTest(long courseProgressTrackerId, int testId);

    TestProgressTrackerDTO updateTestProgress(long courseProgressTrackerId, int testId, TestProgressUpdateDTO updateDTO);

    List<TestReviewDTO> getReviewsPastN(long testProgressTrackerId, int pastMonths);
}
