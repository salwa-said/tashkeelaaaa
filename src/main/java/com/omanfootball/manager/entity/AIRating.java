package com.omanfootball.manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Table(name = "ai_rating")
@Data
public class AIRating {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aiRatingId;



    private Integer overall;


    private Integer passing;


    private Integer defense;


    private Integer physical;


    private Double predictionScore;


    private String recommendation;



    @Column(columnDefinition = "TEXT")
    private String analysis;



    private LocalDateTime createdAt;



    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", unique = true)
    @JsonIgnore
    private Player player;



    @PrePersist
    public void createDate() {
        this.createdAt = LocalDateTime.now();
    }

}