package com.omanfootball.manager.dto;


import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class PlayerResponse {



    private Long playerId;



    private String name;



    private String position;



    private Integer age;



    private Double price;



    private Double overallRating;



    private Long clubId;



}