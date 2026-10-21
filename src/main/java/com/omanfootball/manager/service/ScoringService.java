package com.omanfootball.manager.service;


import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class ScoringService {

    private final PointsCalculator pointsCalculator;



    public int calculatePoints(
            int goals,
            int assists,
            int yellowCards,
            int redCards,
            boolean cleanSheet,
            boolean teamWin,
            boolean manOfMatch,
            double aiRating
    ) {


        return pointsCalculator.calculatePoints(goals, assists, yellowCards, redCards,
                cleanSheet, teamWin, manOfMatch, aiRating);


    }


}
