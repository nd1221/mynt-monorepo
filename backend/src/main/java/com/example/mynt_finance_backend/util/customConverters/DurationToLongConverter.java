package com.example.mynt_finance_backend.util.customConverters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.Duration;

@Converter
public class DurationToLongConverter implements AttributeConverter<Duration, Long> {

    @Override
    public Long convertToDatabaseColumn(Duration duration) {
        return duration == null ? null : duration.toMillis();
    }

    @Override
    public Duration convertToEntityAttribute(Long millis) {
        return millis == null ? null : Duration.ofMillis(millis);
    }
}
