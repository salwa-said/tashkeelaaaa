package com.omanfootball.manager.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "player_performance")
@Data
public class PlayerPerformance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long performanceId;



    private Integer goals = 0;



    private Integer assists = 0;



    private Integer yellowCards = 0;



    private Integer redCards = 0;



    private Double passAccuracy = 0.0;



    private Integer minutesPlayed = 0;



    private Boolean cleanSheet = false;



    private Boolean manOfMatch = false;

    private Double aiRating = 0.0;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    @JsonIgnore
    private Match match;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    @JsonIgnore
    private Player player;
}
