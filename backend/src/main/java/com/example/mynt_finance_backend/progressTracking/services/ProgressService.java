package com.example.mynt_finance_backend.progressTracking.services;

public interface ProgressService {
    default long calculateRollingAverageCumulative(long prevCumAvg, long newValue, int currentN) {
        return (newValue + (prevCumAvg * currentN)) / (currentN + 1);
    }

    default double calculateRollingAverageCumulative(double prevCumAvg, double newValue, int currentN) {
        return (newValue + (prevCumAvg * currentN)) / (currentN + 1);
    }

    default double calculateRollingAverageBoolean(double prevAccuracy, int currentN, boolean success) {
        double currentProportion = prevAccuracy / 100;
        double newProportion = (currentProportion * currentN + (success ? 1 : 0)) / (currentN + 1);
        return newProportion * 100;
    }
}
