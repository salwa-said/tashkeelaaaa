package com.omanfootball.manager.dto;


import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class UserChallengeResponse {



    private Long id;



    private Long userId;



    private Long challengeId;



    private Boolean completed;



    private Integer earnedPoints;



}