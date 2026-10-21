package com.omanfootball.manager.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record NewPlayerPerformanceRequest(
        @NotNull(message = "Player details are required") @Valid PlayerRequest player,
        @NotNull(message = "Choose a match") @Min(value = 1, message = "Choose a valid match")
        Long matchId,
        @NotNull(message = "Performance details are required") @Valid PerformanceData performance
) {
    public record PerformanceData(
            @NotNull @Min(0) @Max(30) Integer goals,
            @NotNull @Min(0) @Max(30) Integer assists,
            @NotNull @Min(0) @Max(130) Integer minutesPlayed,
            @NotNull @DecimalMin("0.0") @DecimalMax("100.0") Double passAccuracy,
            @NotNull @Min(0) @Max(10) Integer yellowCards,
            @NotNull @Min(0) @Max(5) Integer redCards,
            @NotNull Boolean cleanSheet,
            @NotNull Boolean manOfMatch,
            @NotNull @DecimalMin("0.0") @DecimalMax("10.0") Double aiRating
    ) {}
}
