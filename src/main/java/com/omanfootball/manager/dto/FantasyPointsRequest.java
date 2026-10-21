package com.omanfootball.manager.dto;


import lombok.Data;



@Data
public class FantasyPointsRequest {



    private Long playerId;



    private Long teamId;



    private Long matchId;



    private Integer points;



    private String explanation;



}