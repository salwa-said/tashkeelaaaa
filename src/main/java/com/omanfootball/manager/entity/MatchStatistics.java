package com.omanfootball.manager.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "match_statistics")
@Data
public class MatchStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statisticsId;



    private Integer possessionHome;



    private Integer possessionAway;



    private Integer shotsHome;



    private Integer shotsAway;



    private Integer shotsOnTargetHome;



    private Integer shotsOnTargetAway;



    private Integer cornersHome;



    private Integer cornersAway;



    private Integer foulsHome;



    private Integer foulsAway;



    private Integer yellowCardsHome;



    private Integer yellowCardsAway;



    private Integer redCardsHome;



    private Integer redCardsAway;



    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", unique = true)
    @JsonIgnore
    private Match match;
}
