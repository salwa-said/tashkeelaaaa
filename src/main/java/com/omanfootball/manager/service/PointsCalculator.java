package com.omanfootball.manager.service;

import org.springframework.stereotype.Service;

@Service
public class PointsCalculator {

    public int calculatePoints(int goals, int assists, int yellowCards, int redCards,
                               boolean cleanSheet, boolean teamWin, boolean manOfMatch,
                               double aiRating) {
        return calculateBreakdown(goals, assists, yellowCards, redCards, cleanSheet,
                teamWin, manOfMatch, aiRating).totalPoints();
    }

    public PointsBreakdown calculateBreakdown(int goals, int assists, int yellowCards,
                                               int redCards, boolean cleanSheet,
                                               boolean teamWin, boolean manOfMatch,
                                               double aiRating) {
        int goalsPoints = goals * 5;
        int assistsPoints = assists * 3;
        int cleanSheetPoints = cleanSheet ? 4 : 0;
        int winPoints = teamWin ? 2 : 0;
        int manOfMatchPoints = manOfMatch ? 5 : 0;
        int aiRatingPoints = aiRating >= 8.0 ? 3 : 0;
        int yellowCardPoints = -yellowCards;
        int redCardPoints = -redCards * 3;
        int totalPoints = goalsPoints + assistsPoints + cleanSheetPoints + winPoints
                + manOfMatchPoints + aiRatingPoints + yellowCardPoints + redCardPoints;
        return new PointsBreakdown(goalsPoints, assistsPoints, cleanSheetPoints, winPoints,
                manOfMatchPoints, aiRatingPoints, yellowCardPoints, redCardPoints, totalPoints);
    }

    public record PointsBreakdown(int goalsPoints, int assistsPoints, int cleanSheetPoints,
                                  int winPoints, int manOfMatchPoints, int aiRatingPoints,
                                  int yellowCardPoints, int redCardPoints, int totalPoints) {}
}
