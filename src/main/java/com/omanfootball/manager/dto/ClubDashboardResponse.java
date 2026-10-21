package com.omanfootball.manager.dto;


import lombok.Data;


import java.time.LocalDateTime;



@Data
public class ClubDashboardResponse {



    private Long dashboardId;



    private String clubName;



    private Double squadStrength;



    private String teamForm;



    private String topPlayer;



    private String weaknesses;



    private Double averageRating;



    private Integer totalPlayers;



    private LocalDateTime lastUpdated;



}