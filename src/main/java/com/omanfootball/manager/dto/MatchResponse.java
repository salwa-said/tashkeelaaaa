package com.omanfootball.manager.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MatchResponse {

    private Long matchId;


    private LocalDateTime matchDate;


    private String venue;


    private Long homeClubId;


    private Long awayClubId;


    private int homeScore;


    private int awayScore;


    private String status;
}
