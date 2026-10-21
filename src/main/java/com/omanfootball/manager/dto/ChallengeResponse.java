package com.omanfootball.manager.dto;


import lombok.Data;



@Data
public class ChallengeResponse {



    private Long challengeId;



    private String title;



    private String description;



    private String type;



    private Integer rewardPoints;



}