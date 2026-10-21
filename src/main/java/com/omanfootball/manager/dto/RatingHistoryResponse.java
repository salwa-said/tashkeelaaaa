package com.omanfootball.manager.dto;


import lombok.AllArgsConstructor;
import lombok.Data;


import java.time.LocalDateTime;



@Data
@AllArgsConstructor
public class RatingHistoryResponse {



    private Long historyId;



    private Long playerId;



    private Integer oldRating;



    private Integer newRating;



    private String reason;



    private LocalDateTime changeDate;



}