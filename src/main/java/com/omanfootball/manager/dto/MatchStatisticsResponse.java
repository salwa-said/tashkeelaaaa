package com.omanfootball.manager.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MatchStatisticsResponse {

    private Long statisticsId;



    private Long matchId;



    private Integer possessionHome;



    private Integer possessionAway;



    private Integer shotsHome;



    private Integer shotsAway;



    private Integer cornersHome;



    private Integer cornersAway;
}
