package com.omanfootball.manager.dto;


import lombok.Data;



@Data
public class PlayerComparisonResponse {



    private String playerOneName;



    private String playerTwoName;



    private int playerOneGoals;



    private int playerTwoGoals;



    private double playerOneRating;



    private double playerTwoRating;



    private double playerOneFantasyPoints;



    private double playerTwoFantasyPoints;



    private String aiExplanation;



}