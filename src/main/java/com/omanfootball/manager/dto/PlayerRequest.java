package com.omanfootball.manager.dto;


import lombok.Data;



@Data
public class PlayerRequest {



    private String name;



    private String position;



    private Integer age;



    private Double price;



    private Double overallRating;



    private Long clubId;



}