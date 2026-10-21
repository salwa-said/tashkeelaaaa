package com.omanfootball.manager.dto;


import lombok.Data;

@Data
public class StartingXIResponse {

    private String formation;



    private List<String> players;



    private String aiReason;

}
