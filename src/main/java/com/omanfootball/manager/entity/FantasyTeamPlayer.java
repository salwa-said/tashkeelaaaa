package com.omanfootball.manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Table(name = "fantasy_team_player")
@Data
public class FantasyTeamPlayer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    private String position;

    private String formationSlot;



    private Double price;



    private Integer overallRating;



    private Boolean isCaptain = false;



    private LocalDateTime selectedAt;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    @JsonIgnore
    private FantasyTeam fantasyTeam;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    @JsonIgnore
    private Player player;



    @PrePersist
    public void onCreate() {

        this.selectedAt = LocalDateTime.now();


        if (this.isCaptain == null) {
            this.isCaptain = false;
        }

    }

}
