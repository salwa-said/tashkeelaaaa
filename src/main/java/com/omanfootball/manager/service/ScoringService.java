package com.omanfootball.manager.service;


import org.springframework.stereotype.Service;



@Service
public class ScoringService {



    public int calculatePoints(
            int goals,
            int assists,
            int appearances
    ) {


        return (goals * 5)
                + (assists * 3)
                + appearances;


    }


}