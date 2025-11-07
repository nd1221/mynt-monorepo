package com.example.mynt_finance_backend.progressTracking.services;

import com.example.mynt_finance_backend.progressTracking.controllers.utils.CustomSessionSearchParam;
import com.example.mynt_finance_backend.progressTracking.controllers.utils.ReviewSessionSearchParam;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.QuestionProgressAggregateDTO;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.ReviewSessionMetadataDTO;
import com.example.mynt_finance_backend.progressTracking.domain.dtos.ReviewSessionRequestDTO;

import java.util.List;

public interface ReviewSessionService {

    ReviewSessionMetadataDTO loadReviewSession(long courseProgressTrackerId, ReviewSessionSearchParam params);

    ReviewSessionMetadataDTO loadCustomSession(long courseProgressTrackerId, CustomSessionSearchParam params);

    List<QuestionProgressAggregateDTO> getSession(ReviewSessionRequestDTO sessionRequest);
}
