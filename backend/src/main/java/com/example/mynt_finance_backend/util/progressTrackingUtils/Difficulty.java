package com.example.mynt_finance_backend.util.progressTrackingUtils;

public enum Difficulty {

    UPPER_BOUND(100.0),
    EASY(66.66),
    MEDIUM(33.33),
    HARD(0.0);

    private final double threshold;

    Difficulty(double threshold) {
        this.threshold = threshold;
    }

    public double getThreshold() {
        return this.threshold;
    }
}
