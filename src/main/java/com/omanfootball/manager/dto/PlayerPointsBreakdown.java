package com.omanfootball.manager.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PlayerPointsBreakdown(
        Long playerId,
        String playerName,
        Integer totalPoints,
        List<MatchBreakdown> matches
) {
    public record MatchBreakdown(
            String match,
            LocalDateTime matchDate,
            Integer goals,
            Integer assists,
            Integer yellowCards,
            Integer redCards,
            Boolean cleanSheet,
            Boolean teamWon,
            Boolean manOfMatch,
            Double aiRating,
            Integer goalsPoints,
            Integer assistsPoints,
            Integer cleanSheetPoints,
            Integer winPoints,
            Integer manOfMatchPoints,
            Integer aiRatingPoints,
            Integer yellowCardPoints,
            Integer redCardPoints,
            Integer totalPoints
    ) {}
}
