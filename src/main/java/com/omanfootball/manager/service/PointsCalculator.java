package com.omanfootball.manager.service;


import org.springframework.stereotype.Service;



@Service
public class PointsCalculator {


    public int calculatePoints(
            int goals,
            int assists,
            int yellowCards,
            int redCards,
            boolean cleanSheet,
            boolean manOfMatch
    ) {


        int points = 0;



        points += goals * 5;



        points += assists * 3;




        if (cleanSheet) {

            points += 4;

        }




        if (manOfMatch) {

            points += 5;

        }




        points -= yellowCards;



        points -= redCards * 3;



        return points;

    }


}