package com.omanfootball.manager.dto;

public record LeaderboardEntryResponse(
        int rank,
        String managerName,
        String teamName,
        Integer totalPoints,
        Double overallRating
) {}
