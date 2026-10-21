package com.omanfootball.manager.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "starting_xi_recommendations")
@Data
public class StartingXIRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommendationId;



    private String formation;



    @Column(columnDefinition = "TEXT")
    private String recommendedPlayers;



    @Column(columnDefinition = "TEXT")
    private String aiReason;



    private Double confidenceScore;



    private LocalDateTime createdAt;



    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", unique = true)
    @JsonIgnore
    private Match match;



    @PrePersist
    public void createDate() {
        this.createdAt = LocalDateTime.now();
    }
}
