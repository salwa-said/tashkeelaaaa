package com.omanfootball.manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Table(name = "fantasy_points")
@Data
public class FantasyPoints {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;



    private Integer points;



    private Double multiplier;



    @Column(columnDefinition = "TEXT")
    private String explanation;



    private LocalDateTime createdAt;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    @JsonIgnore
    private Match match;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    @JsonIgnore
    private FantasyTeam fantasyTeam;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    @JsonIgnore
    private Player player;



    @PrePersist
    public void createDate() {
        this.createdAt = LocalDateTime.now();
    }

}