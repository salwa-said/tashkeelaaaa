package com.omanfootball.manager.dto;


import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class ClubResponse {



    private Long clubId;



    private String name;



    private String country;



    private String logo;



}