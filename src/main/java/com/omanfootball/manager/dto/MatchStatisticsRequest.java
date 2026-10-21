package com.omanfootball.manager.dto;


import lombok.Data;

@Data
public class MatchStatisticsRequest {
    private Long matchId;



    private Integer possessionHome;



    private Integer possessionAway;



    private Integer shotsHome;



    private Integer shotsAway;



    private Integer cornersHome;



    private Integer cornersAway;

}
