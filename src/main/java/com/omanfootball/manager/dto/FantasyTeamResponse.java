package com.omanfootball.manager.dto;


import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class FantasyTeamResponse {



    private Long teamId;



    private String teamName;



    private Double budget;



    private Double totalPoints;



    private Long userId;



}