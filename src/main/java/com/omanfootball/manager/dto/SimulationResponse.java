package com.omanfootball.manager.dto;


import lombok.Data;



@Data
public class SimulationResponse {



    private String originalTeam;



    private String changes;



    private Double oldPoints;



    private Double newPoints;



    private String aiExplanation;



}