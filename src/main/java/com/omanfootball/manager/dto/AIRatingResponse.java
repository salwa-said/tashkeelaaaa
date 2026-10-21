package com.omanfootball.manager.dto;


import lombok.AllArgsConstructor;
import lombok.Data;


import java.time.LocalDateTime;



@Data
@AllArgsConstructor
public class AIRatingResponse {



    private Long aiRatingId;



    private Long playerId;



    private Integer overall;



    private Integer passing;



    private Integer defense;



    private Integer physical;



    private String analysis;



    private LocalDateTime createdAt;



}