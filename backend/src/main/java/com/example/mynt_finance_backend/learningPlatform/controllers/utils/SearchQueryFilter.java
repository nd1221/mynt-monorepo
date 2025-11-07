package com.example.mynt_finance_backend.learningPlatform.controllers.utils;

import com.example.mynt_finance_backend.learningPlatform.domain.entityEnums.ContentDifficulty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public class SearchQueryFilter {

    private String type;

    private List<String> tags;

    private List<ContentDifficulty> difficulty;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime from;

    public SearchQueryFilter(String type, List<String> tags, List<ContentDifficulty> difficulty, LocalDateTime from) {
        this.type = type;
        this.tags = tags;
        this.difficulty = difficulty;
        this.from = from;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<ContentDifficulty> getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(List<ContentDifficulty> difficulty) {
        this.difficulty = difficulty;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime date) {
        this.from = date;
    }
}
