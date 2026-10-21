package com.omanfootball.manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Table(name = "shared_fantasy_teams")
@Data
public class SharedFantasyTeam {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shareId;



    @Column(unique = true)
    private String shareCode;



    private String shareUrl;



    private LocalDateTime createdAt;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    @JsonIgnore
    private FantasyTeam fantasyTeam;



    @PrePersist
    public void createDate() {
        this.createdAt = LocalDateTime.now();
    }

}