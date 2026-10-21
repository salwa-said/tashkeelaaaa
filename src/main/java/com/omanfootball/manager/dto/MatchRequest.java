package com.omanfootball.manager.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MatchRequest {

    private LocalDate matchDate;



    private String round;



    private String venue;



    private Long homeClubId;



    private Long awayClubId;

}
