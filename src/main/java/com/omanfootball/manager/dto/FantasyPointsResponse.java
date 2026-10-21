package com.omanfootball.manager.dto;


import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class FantasyPointsResponse {



    private Long pointId;



    private Long playerId;



    private Long teamId;



    private Long matchId;



    private Integer points;



    private String explanation;



}