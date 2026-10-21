package com.omanfootball.manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "ai_fantasy_points")
@Data
public class AIFantasyPoints {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Double predictedPoints;


    private Double aiRating;

    @Transient
    private Integer goalsAnalyzed;

    @Transient
    private Integer assistsAnalyzed;

    @Transient
    private Integer appearancesAnalyzed;

    @Transient
    private Integer playerRating;

    @Transient
    private String playerRatingAnalysis;


    private String recommendation;


    @Column(columnDefinition = "TEXT")
    private String explanation;


    private LocalDateTime createdAt;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    @JsonIgnore
    private Player player;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    @JsonIgnore
    private Match match;



    @PrePersist
    public void createDate() {
        this.createdAt = LocalDateTime.now();
    }

}
